package cn.ox85.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.ref.SoftReference;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author alec zhang
 */
public class JImagePanel extends JPanel {

    public static enum Style {
        CENTERED, TILED, SCALED, SCALED_KEEP_ASPECT_RATIO
    }

    private static final Logger LOG = Logger.getLogger(JImagePanel.class.getName());

    /**
     * Text informing the user that clicking on this component will allow them
     * to set the image
     */
    private static final String TEXT = "<html><i><b>Click here<br>to set the image</b></i></html>";

    /**
     * The image to draw
     */
    private SoftReference<Image> img = new SoftReference<Image>(null);

    /**
     * If true, then the image can be changed. Perhaps a better name is
     * &quot;readOnly&quot;, but editable was chosen to be more consistent with
     * other Swing components.
     */
    private boolean editable = false;

    /**
     * The mouse handler that is used if the component is editable
     */
    private MouseHandler mhandler = new MouseHandler();

    /**
     * Specifies how to draw the image, i.e. what kind of Style to use when
     * drawing
     */
    private Style style = Style.CENTERED;

    private Image defaultImage;

    private Callable<Image> imageLoader;

    private static final ExecutorService service = Executors.newFixedThreadPool(5);

    public JImagePanel() {
    }

    /**
     * Sets the image to use for the background of this panel. This image is
     * painted whether the panel is opaque or translucent.
     *
     * @param image if null, clears the image. Otherwise, this will set the
     *              image to be painted. If the preferred size has not been explicitly
     *              set, then the image dimensions will alter the preferred size of
     *              the panel.
     */
    public void setImage(Image image) {
        if (image != img.get()) {
            Image oldImage = img.get();
            img = new SoftReference<Image>(image);
            firePropertyChange("image", oldImage, img);
            invalidate();
            repaint();
        }
    }

    /**
     * @return the image used for painting the background of this panel
     */
    public Image getImage() {
        Image image = img.get();

        //TODO perhaps we should have a default image loader?
        if (image == null && imageLoader != null) {
            try {
                image = imageLoader.call();
                img = new SoftReference<Image>(image);
            } catch (Exception e) {
                LOG.log(Level.WARNING, "", e);
            }
        }
        return image;
    }

    /**
     * @param editable
     */
    public void setEditable(boolean editable) {
        if (editable != this.editable) {
            // if it was editable, remove the mouse handler
            if (this.editable) {
                removeMouseListener(mhandler);
            }
            this.editable = editable;
            // if it is now editable, add the mouse handler
            if (this.editable) {
                addMouseListener(mhandler);
            }
            setToolTipText(editable ? TEXT : "");
            firePropertyChange("editable", !editable, editable);
            repaint();
        }
    }

    /**
     * @return whether the image for this panel can be changed or not via the
     *         UI. setImage may still be called, even if <code>isEditable</code>
     *         returns false.
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Sets what style to use when painting the image
     *
     * @param s
     */
    public void setStyle(Style s) {
        if (style != s) {
            Style oldStyle = style;
            style = s;
            firePropertyChange("style", oldStyle, s);
            repaint();
        }
    }

    /**
     * @return the Style used for drawing the image (CENTERED, TILED, etc).
     */
    public Style getStyle() {
        return style;
    }

    /**
     * {@inheritDoc}
     * The old property value in PCE fired by this method might not be always correct!
     */
    @Override
    public Dimension getPreferredSize() {
        if (!isPreferredSizeSet() && img != null) {
            Image img = this.img.get();
            // was img GCed in the mean time?
            if (img != null) {
                // it has not been explicitly set, so return the width/height of
                // the image
                int width = img.getWidth(null);
                int height = img.getHeight(null);
                if (width == -1 || height == -1) {
                    return super.getPreferredSize();
                }
                Insets insets = getInsets();
                width += insets.left + insets.right;
                height += insets.top + insets.bottom;
                return new Dimension(width, height);
            }
        }
        return super.getPreferredSize();
    }

    /**
     * Overridden to paint the image on the panel
     *
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Image img = this.img.get();
        if (img == null && imageLoader != null) {
            // schedule for loading (will repaint itself once loaded)
            // have to use new future task every time as it holds strong
            // reference to the object it retrieved and doesn't allow to reset
            // it.
            service.execute(new FutureTask<Image>(imageLoader) {

                @Override
                protected void done() {
                    super.done();

                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            try {
                                JImagePanel.this.setImage(get());
                            } catch (InterruptedException e) {
                                // ignore - canceled image load
                            } catch (ExecutionException e) {
                                LOG.log(Level.WARNING, "", e);
                            }
                        }
                    });
                }

            });
            img = defaultImage;
        }
        if (img != null) {
            final int imgWidth = img.getWidth(null);
            final int imgHeight = img.getHeight(null);
            if (imgWidth == -1 || imgHeight == -1) {
                // image hasn't completed loading, return
                return;
            }

            Insets insets = getInsets();
            final int pw = getWidth() - insets.left - insets.right;
            final int ph = getHeight() - insets.top - insets.bottom;

            switch (style) {
                case CENTERED:
                    Rectangle clipRect = g2.getClipBounds();
                    int imageX = (pw - imgWidth) / 2 + insets.left;
                    int imageY = (ph - imgHeight) / 2 + insets.top;
                    Rectangle r = SwingUtilities.computeIntersection(imageX, imageY, imgWidth, imgHeight, clipRect);
                    if (r.x == 0 && r.y == 0 && (r.width == 0 || r.height == 0)) {
                        return;
                    }
                    // I have my new clipping rectangle "r" in clipRect space.
                    // It is therefore the new clipRect.
                    clipRect = r;
                    // since I have the intersection, all I need to do is adjust the
                    // x & y values for the image
                    int txClipX = clipRect.x - imageX;
                    int txClipY = clipRect.y - imageY;
                    int txClipW = clipRect.width;
                    int txClipH = clipRect.height;

                    g2.drawImage(img, clipRect.x, clipRect.y, clipRect.x + clipRect.width, clipRect.y + clipRect.height, txClipX, txClipY, txClipX + txClipW, txClipY + txClipH, null);
                    break;
                case TILED:
                    g2.translate(insets.left, insets.top);
                    Rectangle clip = g2.getClipBounds();
                    g2.setClip(0, 0, pw, ph);

                    int totalH = 0;

                    while (totalH < ph) {
                        int totalW = 0;

                        while (totalW < pw) {
                            g2.drawImage(img, totalW, totalH, null);
                            totalW += img.getWidth(null);
                        }

                        totalH += img.getHeight(null);
                    }

                    g2.setClip(clip);
                    g2.translate(-insets.left, -insets.top);
                    break;
                case SCALED:
                    g2.drawImage(img, insets.left, insets.top, pw, ph, null);
                    break;
                case SCALED_KEEP_ASPECT_RATIO:
                    int w = pw;
                    int h = ph;
                    final float ratioW = ((float) w) / ((float) imgWidth);
                    final float ratioH = ((float) h) / ((float) imgHeight);

                    if (ratioW < ratioH) {
                        h = (int) (imgHeight * ratioW);
                    } else {
                        w = (int) (imgWidth * ratioH);
                    }

                    final int x = (pw - w) / 2 + insets.left;
                    final int y = (ph - h) / 2 + insets.top;
                    g2.drawImage(img, x, y, w, h, null);
                    break;
                default:
                    LOG.fine("unimplemented");
                    g2.drawImage(img, insets.left, insets.top, this);
                    break;
            }
        }
    }

    /**
     * Handles click events on the component
     */
    private class MouseHandler extends MouseAdapter {
        private Cursor oldCursor;

        private JFileChooser chooser;

        @Override
        public void mouseClicked(MouseEvent evt) {
            if (chooser == null) {
                chooser = new JFileChooser();
            }
            int retVal = chooser.showOpenDialog(JImagePanel.this);
            if (retVal == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                try {
                    setImage(new ImageIcon(file.toURI().toURL()).getImage());
                } catch (Exception ex) {
                }
            }
        }

        @Override
        public void mouseEntered(MouseEvent evt) {
            if (oldCursor == null) {
                oldCursor = getCursor();
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }

        @Override
        public void mouseExited(MouseEvent evt) {
            if (oldCursor != null) {
                setCursor(oldCursor);
                oldCursor = null;
            }
        }
    }

    public void setDefaultImage(Image def) {
        this.defaultImage = def;
    }

    public void setImageLoader(Callable<Image> loadImage) {
        this.imageLoader = loadImage;

    }
}
