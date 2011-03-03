package cn.ox85.ui.components;

import cn.ox85.custom.BookImporter;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.ButtonBarBuilder;
import cn.ox85.ui.util.IconLoader;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * @author alec zhang
 */
public class DatabaseManageDialog extends JDialog {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManageDialog.class);
    private JTextField booklistTextField_;
    private JButton browseButton_;

    public DatabaseManageDialog(Frame owner) {
        super(owner);
        initComponents();
    }

    private void initComponents() {
        JLabel booklistLabel = new JLabel(BundleUtil.getString("LBL_BookList"));
        booklistTextField_ = new JTextField();
        browseButton_ = new JButton(new BrowseAction());
        JButton okButton = new JButton(new ApplyAction());
        JButton cancelButton = new JButton(new CancelAction());

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        JPanel dialogPane = new JPanel(new BorderLayout());
        dialogPane.setBorder(Borders.DIALOG_BORDER);

        CellConstraints cc = new CellConstraints();
        FormLayout layout = new FormLayout(
                "default, $lcgap, 90dlu, $lcgap, default",
                "default");
        PanelBuilder builder = new PanelBuilder(layout);
        builder.add(booklistLabel, cc.xy(1, 1));
        builder.add(booklistTextField_, cc.xy(3, 1));
        builder.add(browseButton_, cc.xy(5, 1));
        builder.setBorder(new TitledBorder(BundleUtil.getString("LBL_BookListImport")));
        dialogPane.add(builder.getPanel(), BorderLayout.CENTER);

        ButtonBarBuilder builder2 = new ButtonBarBuilder();
        builder2.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
        builder2.addGlue();
        builder2.addButton(okButton);
        builder2.addRelatedGap();
        builder2.addButton(cancelButton);
        dialogPane.add(builder2.getPanel(), BorderLayout.SOUTH);

        contentPane.add(dialogPane, BorderLayout.CENTER);
        setTitle(BundleUtil.getString("TITLE_DBManagement"));
        setIconImage(IconLoader.getIcon32("main.png").getImage());
        setModal(true);
        setResizable(false);
        pack();
        setLocationRelativeTo(getOwner());
    }

    private final class BrowseAction extends AbstractAction {

        public BrowseAction() {
            super(BundleUtil.getString("BTN_Browse"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileFilter() {

                @Override
                public boolean accept(File f) {
                    String fileName = f.getName();
                    return f.isDirectory() || fileName.endsWith(".xls");
                }

                @Override
                public String getDescription() {
                    return BundleUtil.getString("DESC_XLSChooser");
                }
            });
            int result = fileChooser.showOpenDialog(DatabaseManageDialog.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                booklistTextField_.setText(fileChooser.getSelectedFile().getAbsolutePath());
            }
        }
    }

    private final class ApplyAction extends AbstractAction {

        private ApplyAction() {
            super(BundleUtil.getString("BTN_OK"));
        }

        public void actionPerformed(ActionEvent e) {
            String file = booklistTextField_.getText();
            boolean success = true;
            try {
                BookImporter.importExcel(file);
            } catch (Exception ex) {
                success = false;
                logger.error("Error happens when import book list.", ex);
                JOptionPane.showMessageDialog(DatabaseManageDialog.this,
                        ex.getMessage(),
                        BundleUtil.getString("TITLE_BookListImportFailed"),
                        JOptionPane.ERROR_MESSAGE);
            }
            if (success) {
                DatabaseManageDialog.this.dispose();
            }
        }
    }

    private final class CancelAction extends AbstractAction {

        private CancelAction() {
            super(BundleUtil.getString("BTN_Cancel"));
        }

        public void actionPerformed(ActionEvent e) {
            DatabaseManageDialog.this.dispose();
        }
    }
}
