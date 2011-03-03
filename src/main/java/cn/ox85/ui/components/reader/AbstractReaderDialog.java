package cn.ox85.ui.components.reader;

import cn.ox85.models.ReaderType;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.ReaderTypeMap;
import cn.ox85.ui.beans.ReaderBean;
import cn.ox85.ui.binding.BindingModel;
import cn.ox85.ui.components.JImagePanel;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.ButtonBarBuilder;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.view.ValidationComponentUtils;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author alec zhang
 */
public abstract class AbstractReaderDialog extends JDialog {

    protected JTextField barCodeTextField_;
    protected JImagePanel photoPanel_;
    protected JButton photoAddButton_;
    protected JButton photoClearButton_;
    protected JComboBox readerTypeComboBox_;
    protected JTextField nameTextField_;
    protected JTextField identityNumTextField_;
    protected JComboBox sexComboBox_;
    protected JComboBox statusComboBox_;
    protected JTextField phoneTextField_;
    protected JTextField emailTextField_;
    protected JTextArea remarksTextArea_;

    protected final BindingModel<ReaderBean> bindingModel_;
    protected byte[] photo_ = null;

    public AbstractReaderDialog(Frame owner) {
        super(owner);
        bindingModel_ = initBindingModel();
        buildPanel();
    }

    protected abstract BindingModel<ReaderBean> initBindingModel();

    private java.util.List<ReaderType> getReaderTypes() {
        SqlSession session = ConnectionFactory.getSession().openSession();
        java.util.List<ReaderType> readerTypes = new ArrayList<ReaderType>();
        try {
            ReaderTypeMap readerTypeDAO = session.getMapper(ReaderTypeMap.class);
            readerTypes = readerTypeDAO.selectReaderTypes();
            session.commit();
        } finally {
            session.close();
        }
        return readerTypes;
    }

    private void initComponents() {
        barCodeTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(ReaderBean.PROPERTYNAME_BARCODE));
        photoPanel_ = new JImagePanel();
        photoAddButton_ = new JButton();
        photoClearButton_ = new JButton();
        ValueModel readerTypeModel = bindingModel_.getBufferedModel(ReaderBean.PROPERTYNAME_TYPE);
        readerTypeComboBox_ = BasicComponentFactory.createComboBox(
                new SelectionInList<ReaderType>(getReaderTypes(), readerTypeModel));
        readerTypeComboBox_.setSelectedIndex(0);
        nameTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(ReaderBean.PROPERTYNAME_NAME));
        identityNumTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(ReaderBean.PROPERTYNAME_IDENTITYNUM));
        ValueModel sexModel = bindingModel_.getBufferedModel(ReaderBean.PROPERTYNAME_SEX);
        sexComboBox_ = BasicComponentFactory.createComboBox(
                new SelectionInList<ReaderBean.Sex>(ReaderBean.SEX_CHOICES, sexModel));
        ValueModel statusModel = bindingModel_.getBufferedModel(ReaderBean.PROPERTYNAME_STATUS);
        statusComboBox_ = BasicComponentFactory.createComboBox(
                new SelectionInList<ReaderBean.Status>(ReaderBean.STATUS_CHOICES, statusModel));
        phoneTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(ReaderBean.PROPERTYNAME_PHONE));
        emailTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(ReaderBean.PROPERTYNAME_EMAIL));
        remarksTextArea_ = BasicComponentFactory.createTextArea(
                bindingModel_.getBufferedModel(ReaderBean.PROPERTYNAME_REMARKS));
    }

    private void initComponentAnnotations() {
        ValidationComponentUtils.setMandatory(barCodeTextField_, true);
        ValidationComponentUtils.setMessageKey(barCodeTextField_, ReaderBean.PROPERTYNAME_BARCODE);
        ValidationComponentUtils.setMandatory(nameTextField_, true);
        ValidationComponentUtils.setMessageKey(nameTextField_, ReaderBean.PROPERTYNAME_NAME);
    }

    private void initEventHandling() {
        photoAddButton_.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileFilter() {

                    @Override
                    public boolean accept(File f) {
                        String fileName = f.getName();
                        return f.isDirectory() ||
                                fileName.endsWith(".png") ||
                                fileName.endsWith(".jpg") ||
                                fileName.endsWith(".ico") ||
                                fileName.endsWith(".bmp");
                    }

                    @Override
                    public String getDescription() {
                        return BundleUtil.getString("DESC_ImageChooser");
                    }
                });
                int result = fileChooser.showOpenDialog(AbstractReaderDialog.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    byte[] byteImage = null;
                    FileInputStream finput = null;
                    ByteArrayOutputStream boutput = new ByteArrayOutputStream();
                    try {
                        finput = new FileInputStream(fileChooser.getSelectedFile());
                        byte[] buf = new byte[512];
                        int length;
                        while ((length = finput.read(buf)) > 0) {
                            boutput.write(buf, 0, length);
                        }
                        byteImage = boutput.toByteArray();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } finally {
                        try {
                            if (finput != null)
                                finput.close();
                            boutput.close();
                        } catch (IOException ignored) {
                        }
                    }
                    if (byteImage == null) {
                        return;
                    }
                    photo_ = byteImage;
                    ImageIcon image = new ImageIcon(byteImage);
                    int panelHeight = photoPanel_.getHeight();
                    int panelWidth = photoPanel_.getWidth();
                    int imageHeight = image.getIconHeight();
                    int imageWidth = image.getIconWidth();
                    if (imageHeight > panelHeight || imageWidth > panelWidth) {
                        photoPanel_.setStyle(JImagePanel.Style.SCALED_KEEP_ASPECT_RATIO);
                    } else {
                        photoPanel_.setStyle(JImagePanel.Style.CENTERED);
                    }
                    photoPanel_.setImage(image.getImage());
                }
            }
        });
        photoClearButton_.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                photo_ = null;
                photoPanel_.setImage(null);
            }
        });
    }

    /**
     * Init widget status, such as enable or disable a widget.
     */
    protected abstract void initWidgetStatus();

    private void buildPanel() {
        initComponents();
        initComponentAnnotations();
        initEventHandling();
        initWidgetStatus();
        JLabel barCodeLabel = new JLabel(BundleUtil.getString("LBL_ReaderBarCode"));
        JLabel photoLabel = new JLabel(BundleUtil.getString("LBL_ReaderPhoto"));
        JLabel readerTypeLabel = new JLabel(BundleUtil.getString("LBL_ReaderType"));
        JLabel nameLabel = new JLabel(BundleUtil.getString("LBL_ReaderName"));
        JLabel identityNumLabel = new JLabel(BundleUtil.getString("LBL_ReaderIdentityNum"));
        JLabel sexLabel = new JLabel(BundleUtil.getString("LBL_ReaderSex"));
        JLabel statusLabel = new JLabel(BundleUtil.getString("LBL_ReaderStatus"));
        JLabel phoneLabel = new JLabel(BundleUtil.getString("LBL_ReaderPhone"));
        JLabel emailLabel = new JLabel(BundleUtil.getString("LBL_ReaderEmail"));
        JLabel remarksLabel = new JLabel(BundleUtil.getString("LBL_ReaderRemarks"));
        JScrollPane remarksScrollPane = new JScrollPane();
        JButton okButton = new JButton(new ApplyAction());
        JButton cancelButton = new JButton(new CancelAction());

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        JPanel dialogPane = new JPanel();
        dialogPane.setBorder(Borders.DIALOG_BORDER);
        dialogPane.setLayout(new BorderLayout());

        CellConstraints cc = new CellConstraints();
        FormLayout layout = new FormLayout(
                "16.0mm, $lcgap, 16.0mm, default:grow",
                "default, default:grow, $lgap, default");
        PanelBuilder pbuilder = new PanelBuilder(layout);
        photoPanel_.setBorder(LineBorder.createBlackLineBorder());
        photoPanel_.setLayout(new BorderLayout());
        pbuilder.add(photoPanel_, cc.xywh(1, 1, 3, 2));
        pbuilder.add(photoAddButton_, cc.xy(1, 4));
        pbuilder.add(photoClearButton_, cc.xy(3, 4));
        photoAddButton_.setText(BundleUtil.getString("BTN_ReaderPhotoAdd"));
        photoClearButton_.setText(BundleUtil.getString("BTN_ReaderPhotoClear"));
        JPanel photoAddPanel = pbuilder.getPanel();

        layout = new FormLayout(
                "default, $lcgap, 160px:grow, $lcgap, right:default, $lcgap, 160px:grow",
                "8*(default, $lgap), 100px:grow");
        pbuilder = new PanelBuilder(layout);
        pbuilder.add(barCodeLabel, cc.xy(1, 1));
        pbuilder.add(barCodeTextField_, cc.xy(3, 1));
        pbuilder.add(photoLabel, cc.xy(5, 1));
        pbuilder.add(photoAddPanel, cc.xywh(7, 1, 1, 11));
        pbuilder.add(readerTypeLabel, cc.xy(1, 3));
        pbuilder.add(readerTypeComboBox_, cc.xy(3, 3));
        pbuilder.add(nameLabel, cc.xy(1, 5));
        pbuilder.add(nameTextField_, cc.xy(3, 5));
        pbuilder.add(identityNumLabel, cc.xy(1, 7));
        pbuilder.add(identityNumTextField_, cc.xy(3, 7));
        pbuilder.add(sexLabel, cc.xy(1, 9));
        pbuilder.add(sexComboBox_, cc.xy(3, 9));
        pbuilder.add(statusLabel, cc.xy(1, 11));
        pbuilder.add(statusComboBox_, cc.xy(3, 11));
        pbuilder.add(phoneLabel, cc.xy(1, 13));
        pbuilder.add(phoneTextField_, cc.xy(3, 13));
        pbuilder.add(emailLabel, cc.xy(5, 13));
        pbuilder.add(emailTextField_, cc.xy(7, 13));
        pbuilder.add(remarksLabel, cc.xy(1, 15));
        remarksScrollPane.setViewportView(remarksTextArea_);
        pbuilder.add(remarksScrollPane, cc.xywh(3, 15, 5, 3));
        JPanel contentPanel = pbuilder.getPanel();
        ValidationComponentUtils.updateComponentTreeMandatoryAndBlankBackground(contentPanel);
        dialogPane.add(bindingModel_.getIconFeedbackPanel(contentPanel), BorderLayout.CENTER);

        ButtonBarBuilder builder2 = new ButtonBarBuilder();
        builder2.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
        builder2.addGlue();
        builder2.addButton(okButton);
        builder2.addRelatedGap();
        builder2.addButton(cancelButton);
        dialogPane.add(builder2.getPanel(), BorderLayout.SOUTH);

        contentPane.add(dialogPane, BorderLayout.CENTER);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModal(true);
        pack();
        setLocationRelativeTo(getOwner());
    }

    private void exit() {
        this.dispose();
    }

    /**
     * Commit the reader data, such as add new reader or update reader.
     *
     * @return true if commit reader successfully
     */
    protected abstract boolean commitReader();

    private final class ApplyAction extends AbstractAction {

        private ApplyAction() {
            super(BundleUtil.getString("BTN_OK"));
        }

        public void actionPerformed(ActionEvent e) {
            bindingModel_.triggerCommit();
            if (bindingModel_.hasValidationErrors() || bindingModel_.hasValidationWarnings()) {
                // Handle unknown errors when validate
                ValidationResult result = bindingModel_.getUnexpectedResult();
                if (result != null) {
                    JOptionPane.showMessageDialog(
                            AbstractReaderDialog.this, result.getMessagesText(),
                            BundleUtil.getString("TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                }
                return;
            }
            if (commitReader()) {
                exit();
            }
        }
    }

    private final class CancelAction extends AbstractAction {

        private CancelAction() {
            super(BundleUtil.getString("BTN_Cancel"));
        }

        public void actionPerformed(ActionEvent e) {
            exit();
        }
    }
}