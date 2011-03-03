package cn.ox85.ui.components.bbr;

import cn.ox85.models.BBR;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.BBRMap;
import cn.ox85.ui.beans.BBRBean;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.ButtonBarBuilder;
import cn.ox85.ui.util.IconFeedbackPanel;
import cn.ox85.ui.util.SimpleValidationSupport;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.jgoodies.validation.view.ValidationComponentUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * @author alec zhang
 */
public class BookReturnDialog extends JDialog {
    private static final Logger logger_ = LoggerFactory.getLogger(BookReturnDialog.class);
    private static final String[] COLUMNS = {
            BundleUtil.getString("CLMN_Id"),
            BundleUtil.getString("CLMN_ReaderBarCode"),
            BundleUtil.getString("CLMN_ReaderName"),
            BundleUtil.getString("CLMN_BookBarCode"),
            BundleUtil.getString("CLMN_BookName"),
            BundleUtil.getString("CLMN_BookBDate"),
            BundleUtil.getString("CLMN_BookRDate")
    };
    private static final int[] PREF_WIDTH = {60, 100, 80, 100, 160, 100, 100};

    private final PresentationModel<BBRBean> presentationModel_ =
            new PresentationModel<BBRBean>(new BBRBean());
    private final ValidationResultModel validationResultModel_ = new DefaultValidationResultModel();

    private JTextField bookBarCodeTextField_;
    private JTextField bookNameTextField_;
    private JTextField readerBarCodeTextField_;
    private JTextField readerNameTextField_;
    private JTextField readerTypeTextField_;
    private JTable bookListTable_;
    private DefaultTableModel bookTableModel_;

    public BookReturnDialog(Frame owner) {
        super(owner);
        buildPanel();
    }

    private void initComponents() {
        bookBarCodeTextField_ = BasicComponentFactory.createTextField(
                presentationModel_.getModel(BBRBean.PROPERTYNAME_BOOKBARCODE), false);
        bookNameTextField_ = BasicComponentFactory.createTextField(
                presentationModel_.getModel(BBRBean.PROPERTYNAME_BOOKNAME));
        readerBarCodeTextField_ = BasicComponentFactory.createTextField(
                presentationModel_.getModel(BBRBean.PROPERTYNAME_READERBARCODE));
        readerNameTextField_ = BasicComponentFactory.createTextField(
                presentationModel_.getModel(BBRBean.PROPERTYNAME_READERNAME));
        readerTypeTextField_ = BasicComponentFactory.createTextField(
                presentationModel_.getModel(BBRBean.PROPERTYNAME_READERTYPE));
        bookTableModel_ = new BookTableModel();
        bookListTable_ = new JTable(bookTableModel_);
    }

    private void initComponentAnnotations() {
        bookNameTextField_.setEditable(false);
        readerBarCodeTextField_.setEditable(false);
        readerNameTextField_.setEditable(false);
        readerTypeTextField_.setEditable(false);
        ValidationComponentUtils.setMandatory(bookBarCodeTextField_, true);
        ValidationComponentUtils.setMessageKey(bookBarCodeTextField_, BBRBean.PROPERTYNAME_BOOKBARCODE);
    }

    private void initEventHandling() {
        bookBarCodeTextField_.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String barCode = presentationModel_.getBean().getBookBarCode();
                    int count = bookTableModel_.getRowCount();
                    for (int i = 0; i < count; i++) {
                        String existBarCode = (String) bookTableModel_.getValueAt(i, 3);
                        if (existBarCode.equals(barCode)) {
                            return;
                        }
                    }
                    new BBRWorker().execute();
                }
            }
        });
    }

    private void buildPanel() {
        initComponents();
        initComponentAnnotations();
        initEventHandling();

        JPanel dialogPane = new JPanel();
        JLabel bookBarCodeLabel = new JLabel(BundleUtil.getString("LBL_BookBarCode"));
        JLabel bookNameLabel = new JLabel(BundleUtil.getString("LBL_BookName"));
        JLabel readerBarCodeLabel = new JLabel(BundleUtil.getString("LBL_ReaderBarCode"));
        JLabel readerNameLabel = new JLabel(BundleUtil.getString("LBL_ReaderName"));
        JLabel readerTypeLabel = new JLabel(BundleUtil.getString("LBL_ReaderType"));
        JLabel bookListLabel = new JLabel(BundleUtil.getString("LBL_BBRList"));
        bookListTable_.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel cm = bookListTable_.getColumnModel();
        for (int i = 0; i < PREF_WIDTH.length; i++) {
            cm.getColumn(i).setPreferredWidth(PREF_WIDTH[i]);
        }
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(bookListTable_);
        JButton okButton = new JButton(new ApplyAction());
        JButton cancelButton = new JButton(new CancelAction());

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        dialogPane.setBorder(Borders.DIALOG_BORDER);
        dialogPane.setLayout(new BorderLayout());

        CellConstraints cc = new CellConstraints();
        FormLayout layout = new FormLayout(
                "default, $lcgap, 80dlu:grow, $lcgap, default, $lcgap, 80dlu:grow",
                "4*(default, $lgap), 80dlu:grow"
        );
        PanelBuilder builder = new PanelBuilder(layout);
        builder.add(bookBarCodeLabel, cc.xy(1, 1));
        builder.add(bookBarCodeTextField_, cc.xy(3, 1));
        builder.add(bookNameLabel, cc.xy(5, 1));
        builder.add(bookNameTextField_, cc.xy(7, 1));
        builder.add(readerBarCodeLabel, cc.xy(1, 3));
        builder.add(readerBarCodeTextField_, cc.xy(3, 3));
        builder.add(readerNameLabel, cc.xy(5, 3));
        builder.add(readerNameTextField_, cc.xy(7, 3));
//        builder.add(readerTypeLabel, cc.xy(1, 5));  TODO: display reader type
//        builder.add(readerTypeTextField_, cc.xy(3, 5));
        builder.add(bookListLabel, cc.xy(1, 7));
        builder.add(scrollPane, cc.xyw(1, 9, 7));
        JPanel contentPanel = builder.getPanel();
        ValidationComponentUtils.updateComponentTreeMandatoryAndBlankBackground(contentPanel);
        dialogPane.add(new IconFeedbackPanel(validationResultModel_, contentPanel),
                BorderLayout.CENTER);

        ButtonBarBuilder builder2 = new ButtonBarBuilder();
        builder2.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
        builder2.addGlue();
        builder2.addButton(okButton);
        builder2.addRelatedGap();
        builder2.addButton(cancelButton);
        dialogPane.add(builder2.getPanel(), BorderLayout.SOUTH);

        contentPane.add(dialogPane, BorderLayout.CENTER);
        setTitle(BundleUtil.getString("TITLE_BookReturn"));
        setModal(true);
        pack();
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public class BookTableModel extends DefaultTableModel {

        public BookTableModel() {
            super(BookReturnDialog.COLUMNS, 0);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }

    private final class ApplyAction extends AbstractAction {

        private ApplyAction() {
            super(BundleUtil.getString("BTN_OK"));
        }

        public void actionPerformed(ActionEvent e) {
            presentationModel_.triggerCommit();
            Vector data = bookTableModel_.getDataVector();
            int count = data.size();
            if (count == 0) return;
            SqlSession session = ConnectionFactory.getSession().openSession();
            Date currentDate = Calendar.getInstance().getTime();
            try {
                BBRMap bbrMap = session.getMapper(BBRMap.class);
                for (int i = 0; i < count; i++) {
                    BBR bbr = new BBR();
                    Vector rowdata = (Vector) data.get(i);
                    bbr.setId((Integer) rowdata.get(0));
                    bbr.setReturned(true);
                    bbr.setRdate(currentDate);
                    bbrMap.updateBBR(bbr);
                }
                session.commit();
                BookReturnDialog.this.dispose();
            } catch (Exception ex) {
                session.rollback();
                JOptionPane.showMessageDialog(
                        BookReturnDialog.this, ex.getMessage(),
                        BundleUtil.getString("TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                logger_.error("Error happens when return books.", ex);
            } finally {
                session.close();
            }
        }
    }

    private final class CancelAction extends AbstractAction {

        private CancelAction() {
            super(BundleUtil.getString("BTN_Cancel"));
        }

        public void actionPerformed(ActionEvent e) {
            BookReturnDialog.this.dispose();
        }
    }

    private class BBRWorker extends SwingWorker<BBR, Object> {

        @Override
        protected BBR doInBackground() throws Exception {
            SqlSession session = ConnectionFactory.getSession().openSession();
            BBR bbr = null;
            String barCode = presentationModel_.getBean().getBookBarCode();
            try {
                BBRMap bbrMap = session.getMapper(BBRMap.class);
                bbr = bbrMap.selectBookReturnByBookBarCode(barCode);
            } finally {
                session.close();
            }
            return bbr;
        }

        @Override
        protected void done() {
            try {
                ValidationResult result = new ValidationResult();
                BBR bbr = get();
                if (bbr == null) {
                    SimpleValidationSupport.addWarning(result,
                            BBRBean.PROPERTYNAME_BOOKBARCODE,
                            BundleUtil.getString("MSG_BookNotBorrowed"));
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Object[] data = new Object[]{
                            bbr.getId(), bbr.getReaderBarCode(), bbr.getReaderName(),
                            bbr.getBookBarCode(), bbr.getBookName(), format.format(bbr.getBdate()),
                            format.format(Calendar.getInstance().getTime())};
                    bookTableModel_.addRow(data);
                    BBRBean bbrModel = presentationModel_.getBean();
                    bbrModel.setBookName(bbr.getBookName());
                    bbrModel.setReaderBarCode(bbr.getReaderBarCode());
                    bbrModel.setReaderName(bbr.getReaderName());
                    bookBarCodeTextField_.selectAll();
                }
                validationResultModel_.setResult(result);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        BookReturnDialog.this, e.getMessage(),
                        BundleUtil.getString("TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                logger_.error("Fail to add book.", e);
            }
        }
    }
}