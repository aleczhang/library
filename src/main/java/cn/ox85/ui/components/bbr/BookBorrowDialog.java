package cn.ox85.ui.components.bbr;

import cn.ox85.models.BBR;
import cn.ox85.models.Book;
import cn.ox85.models.Reader;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.BBRMap;
import cn.ox85.sql.maps.BookMap;
import cn.ox85.sql.maps.ReaderMap;
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
public class BookBorrowDialog extends JDialog {
    private static final Logger logger_ = LoggerFactory.getLogger(BookBorrowDialog.class);
    private static final String[] COLUMNS = {
            BundleUtil.getString("CLMN_Id"),
            BundleUtil.getString("CLMN_BookBarCode"),
            BundleUtil.getString("CLMN_BookName"),
            BundleUtil.getString("CLMN_BookBDate")
    };
    private static final int[] PREF_WIDTH = {60, 100, 160, 100};

    private final PresentationModel<BBRBean> presentationModel_ =
            new PresentationModel<BBRBean>(new BBRBean());
    private final ValidationResultModel validationResultModel_ = new DefaultValidationResultModel();
    private ValidationResult result_ = new ValidationResult();

    private JTextField readerBarCodeTextField_;
    private JTextField bookBarCodeTextField_;
    private JTextField readerNameTextField_;
    private JTextField readerTypeTextField_;
    private JTable bookListTable_;
    private DefaultTableModel bookTableModel_;
    private int readerId_ = 0;

    public BookBorrowDialog(Frame owner) {
        super(owner);
        buildPanel();
    }

    private void initComponents() {
        readerBarCodeTextField_ = BasicComponentFactory.createTextField(
                presentationModel_.getModel(BBRBean.PROPERTYNAME_READERBARCODE), false);
        bookBarCodeTextField_ = BasicComponentFactory.createTextField(
                presentationModel_.getModel(BBRBean.PROPERTYNAME_BOOKBARCODE), false);
        readerNameTextField_ = BasicComponentFactory.createTextField(
                presentationModel_.getModel(BBRBean.PROPERTYNAME_READERNAME));
        readerTypeTextField_ = BasicComponentFactory.createTextField(
                presentationModel_.getModel(BBRBean.PROPERTYNAME_READERTYPE));
        bookTableModel_ = new BookTableModel();
        bookListTable_ = new JTable(bookTableModel_);
    }

    private void initComponentAnnotations() {
        readerNameTextField_.setEditable(false);
        readerTypeTextField_.setEditable(false);
        ValidationComponentUtils.setMandatory(readerBarCodeTextField_, true);
        ValidationComponentUtils.setMessageKey(readerBarCodeTextField_, BBRBean.PROPERTYNAME_READERBARCODE);
        ValidationComponentUtils.setMandatory(bookBarCodeTextField_, true);
        ValidationComponentUtils.setMessageKey(bookBarCodeTextField_, BBRBean.PROPERTYNAME_BOOKBARCODE);
    }

    private void initEventHandling() {
        readerBarCodeTextField_.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    new ReaderWorker().execute();
                }
            }
        });
        bookBarCodeTextField_.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String barCode = presentationModel_.getBean().getBookBarCode();
                    int count = bookTableModel_.getRowCount();
                    for (int i = 0; i < count; i++) {
                        String existBarCode = (String) bookTableModel_.getValueAt(i, 1);
                        if (existBarCode.equals(barCode)) {
                            return;
                        }
                    }
                    new BookWorker().execute();
                }
            }
        });
        bookListTable_.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    int[] rows = bookListTable_.getSelectedRows();
                    for (int i = rows.length - 1; i >= 0; i--) {
                        bookTableModel_.removeRow(rows[i]);
                    }
                }
            }
        });
    }

    private void buildPanel() {
        initComponents();
        initComponentAnnotations();
        initEventHandling();

        JPanel dialogPane = new JPanel();
        JLabel readerBarCodeLabel = new JLabel(BundleUtil.getString("LBL_ReaderBarCode"));
        JLabel bookBarCodeLabel = new JLabel(BundleUtil.getString("LBL_BookBarCode"));
        JLabel readerNameLabel = new JLabel(BundleUtil.getString("LBL_ReaderName"));
        JLabel readerTypeLabel = new JLabel(BundleUtil.getString("LBL_ReaderType"));
        JLabel bookListLabel = new JLabel(BundleUtil.getString("LBL_BBRList"));
        JScrollPane scrollPane = new JScrollPane();
        bookListTable_.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        TableColumnModel cm = bookListTable_.getColumnModel();
        for (int i = 0; i < PREF_WIDTH.length; i++) {
            cm.getColumn(i).setPreferredWidth(PREF_WIDTH[i]);
        }
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
                "3*(default, $lgap), 80dlu:grow"
        );
        PanelBuilder builder = new PanelBuilder(layout);
        builder.add(readerBarCodeLabel, cc.xy(1, 1));
        builder.add(readerBarCodeTextField_, cc.xy(3, 1));
        builder.add(bookBarCodeLabel, cc.xy(5, 1));
        builder.add(bookBarCodeTextField_, cc.xy(7, 1));
        builder.add(readerNameLabel, cc.xy(1, 3));
        builder.add(readerNameTextField_, cc.xy(3, 3));
        builder.add(readerTypeLabel, cc.xy(5, 3));
        builder.add(readerTypeTextField_, cc.xy(7, 3));
        builder.add(bookListLabel, cc.xy(1, 5));
        builder.add(scrollPane, cc.xyw(1, 7, 7));
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
        setTitle(BundleUtil.getString("TITLE_BookBorrow"));
        setModal(true);
        pack();
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public class BookTableModel extends DefaultTableModel {

        public BookTableModel() {
            super(BookBorrowDialog.COLUMNS, 0);
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
            if (result_.hasErrors() || result_.hasWarnings() || readerId_ == 0) {
                return;
            }
            presentationModel_.triggerCommit();
            SqlSession session = ConnectionFactory.getSession().openSession();
            Vector data = bookTableModel_.getDataVector();
            int count = data.size();
            Date currentDate = Calendar.getInstance().getTime();
            try {
                BBRMap bbrMap = session.getMapper(BBRMap.class);
                BookMap bookMap = session.getMapper(BookMap.class);
                for (int i = 0; i < count; i++) {
                    BBR bbr = new BBR();
                    Vector rowdata = (Vector) data.get(i);
                    int bookId = (Integer) rowdata.get(0);
                    bbr.setBookId(bookId);
                    bbr.setReaderId(readerId_);
                    bbr.setBdate(currentDate);
                    bbrMap.insertBBR(bbr);
                    bookMap.setBookBorrowed(bookId);
                }
                session.commit();
                BookBorrowDialog.this.dispose();
            } catch (Exception ex) {
                session.rollback();
                JOptionPane.showMessageDialog(
                        BookBorrowDialog.this, ex.getMessage(),
                        BundleUtil.getString("TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                logger_.error("Error happens when borrow books.", ex);
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
            BookBorrowDialog.this.dispose();
        }
    }

    private class ReaderWorker extends SwingWorker<Reader, Object> {

        @Override
        protected Reader doInBackground() throws Exception {
            SqlSession session = ConnectionFactory.getSession().openSession();
            Reader reader = null;
            String barCode = presentationModel_.getBean().getReaderBarCode();
            try {
                reader = session.getMapper(ReaderMap.class).selectReaderByBarCode(barCode);
            } finally {
                session.close();
            }
            return reader;
        }

        @Override
        protected void done() {
            ValidationResult result = new ValidationResult();
            try {
                Reader reader = get();
                if (reader == null) {
                    SimpleValidationSupport.addWarning(result,
                            BBRBean.PROPERTYNAME_READERBARCODE,
                            BundleUtil.getString("MSG_ReaderNotExist"));
                } else {
                    presentationModel_.setValue(BBRBean.PROPERTYNAME_READERNAME,
                            reader.getName());
                    presentationModel_.setValue(BBRBean.PROPERTYNAME_READERTYPE,
                            reader.getReaderType().getName());
                    readerId_ = reader.getId();
                    int count = bookTableModel_.getRowCount();
                    for (int i = count - 1; i >= 0; i--) {
                        bookTableModel_.removeRow(i);
                    }
                    bookBarCodeTextField_.grabFocus();
                }
                result.addAllFrom(result_.subResult(BBRBean.PROPERTYNAME_BOOKBARCODE));
                result_ = result;
                validationResultModel_.setResult(result_);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        BookBorrowDialog.this, e.getMessage(),
                        BundleUtil.getString("TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                logger_.error("Fail to get the reader.", e);
            }
        }
    }

    private class BookWorker extends SwingWorker<Book, Object> {

        @Override
        protected Book doInBackground() throws Exception {
            SqlSession session = ConnectionFactory.getSession().openSession();
            Book book = null;
            String barCode = presentationModel_.getBean().getBookBarCode();
            try {
                book = session.getMapper(BookMap.class).selectBookByBarCode(barCode);
            } finally {
                session.close();
            }
            return book;
        }

        @Override
        protected void done() {
            ValidationResult result = new ValidationResult();
            try {
                Book book = get();
                if (book == null) {
                    SimpleValidationSupport.addWarning(result,
                            BBRBean.PROPERTYNAME_BOOKBARCODE,
                            BundleUtil.getString("MSG_BookNotExist"));
                } else if (book.getStatus() == Book.STATUS_OUT) {
                    SimpleValidationSupport.addWarning(result,
                            BBRBean.PROPERTYNAME_BOOKBARCODE,
                            BundleUtil.getString("MSG_BookAlreadyBorrowed"));
                } else {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Object[] row = new Object[]{
                            book.getId(), book.getBarCode(),
                            book.getName(), format.format(Calendar.getInstance().getTime())};
                    bookTableModel_.addRow(row);
                    bookBarCodeTextField_.selectAll();
                }
                result.addAllFrom(result_.subResult(BBRBean.PROPERTYNAME_READERBARCODE));
                result_ = result;
                validationResultModel_.setResult(result_);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        BookBorrowDialog.this, e.getMessage(),
                        BundleUtil.getString("TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                logger_.error("Fail to add book.", e);
            }
        }
    }
}
