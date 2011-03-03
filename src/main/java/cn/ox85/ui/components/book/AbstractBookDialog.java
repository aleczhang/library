package cn.ox85.ui.components.book;

import cn.ox85.ui.beans.BookBean;
import cn.ox85.ui.binding.BindingModel;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.ButtonBarBuilder;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.michaelbaranov.microba.calendar.DatePicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author alec zhang
 */
public abstract class AbstractBookDialog extends JDialog {

    protected JTextField barCodeTextField_;
    protected JTextField isbnTextField_;
    protected JTextField nameTextField_;
    protected JTextField publisherTextField_;
    protected JTextField authorTextField_;
    protected JTextField translatorTextField_;
    protected JSpinner priceSpinner_;
    protected DatePicker pubDatePicker_;
    protected JTextField callNumTextField_;
    protected JSpinner editionSpinner_;
    protected JTextField seriesTextField_;
    protected JTextField volumeTextField_;
    protected JSpinner pageNumSpinner_;
    protected JTextField classifierTextField_;
    protected JTextArea summaryTextArea_;
    protected JTextArea remarksTextArea_;

    protected final BindingModel<BookBean> bindingModel_;

    public AbstractBookDialog(Frame owner) {
        super(owner);
        bindingModel_ = initBindingModel();
        buildPanel();
    }

    protected abstract BindingModel<BookBean> initBindingModel();

    private void initComponents() {
        barCodeTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getModel(BookBean.PROPERTYNAME_BARCODE));
        isbnTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(BookBean.PROPERTYNAME_ISBN));
        nameTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(BookBean.PROPERTYNAME_NAME));
        publisherTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(BookBean.PROPERTYNAME_PUBLISHER));
        authorTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(BookBean.PROPERTYNAME_AUTHOR));
        translatorTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(BookBean.PROPERTYNAME_TRANSLATOR));
        priceSpinner_ = new JSpinner(new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 1.0));
        pubDatePicker_ = new DatePicker();
        callNumTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(BookBean.PROPERTYNAME_CALLNUM));
        editionSpinner_ = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        seriesTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(BookBean.PROPERTYNAME_SERIES));
        volumeTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(BookBean.PROPERTYNAME_VOLUME));
        pageNumSpinner_ = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        classifierTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(BookBean.PROPERTYNAME_CLASSIFIER));
        summaryTextArea_ = BasicComponentFactory.createTextArea(
                bindingModel_.getBufferedModel(BookBean.PROPERTYNAME_SUMMARY));
        remarksTextArea_ = BasicComponentFactory.createTextArea(
                bindingModel_.getBufferedModel(BookBean.PROPERTYNAME_REMARKS));
    }

    private void initComponentAnnotations() {
        ValidationComponentUtils.setMandatory(barCodeTextField_, true);
        ValidationComponentUtils.setMessageKey(barCodeTextField_, BookBean.PROPERTYNAME_BARCODE);
        ValidationComponentUtils.setMandatory(isbnTextField_, true);
        ValidationComponentUtils.setMessageKey(isbnTextField_, BookBean.PROPERTYNAME_ISBN);
        ValidationComponentUtils.setMandatory(nameTextField_, true);
        ValidationComponentUtils.setMessageKey(nameTextField_, BookBean.PROPERTYNAME_NAME);
        ValidationComponentUtils.setMandatory(publisherTextField_, true);
        ValidationComponentUtils.setMessageKey(publisherTextField_, BookBean.PROPERTYNAME_PUBLISHER);
        ValidationComponentUtils.setMandatory(authorTextField_, true);
        ValidationComponentUtils.setMessageKey(authorTextField_, BookBean.PROPERTYNAME_AUTHOR);
        ValidationComponentUtils.setMandatory(callNumTextField_, true);
        ValidationComponentUtils.setMessageKey(callNumTextField_, BookBean.PROPERTYNAME_CALLNUM);
    }

    private void buildPanel() {
        initComponents();
        initComponentAnnotations();

        JLabel barCodeLabel = new JLabel(BundleUtil.getString("LBL_BookBarCode"));
        JLabel isbnLabel = new JLabel(BundleUtil.getString("LBL_BookISBN"));
        JLabel nameLabel = new JLabel(BundleUtil.getString("LBL_BookName"));
        JLabel publisherLabel = new JLabel(BundleUtil.getString("LBL_BookPublisher"));
        JLabel authorLabel = new JLabel(BundleUtil.getString("LBL_BookAuthor"));
        JLabel translatorLabel = new JLabel(BundleUtil.getString("LBL_BookTranslator"));
        JLabel priceLabel = new JLabel(BundleUtil.getString("LBL_BookPrice"));
        JLabel pubDateLabel = new JLabel(BundleUtil.getString("LBL_BookPubDate"));
        JLabel callNumLabel = new JLabel(BundleUtil.getString("LBL_BookCallNum"));
        JLabel editionLabel = new JLabel(BundleUtil.getString("LBL_BookEdition"));
        JLabel seriesLabel = new JLabel(BundleUtil.getString("LBL_BookSeries"));
        JLabel volumeLabel = new JLabel(BundleUtil.getString("LBL_BookVolume"));
        JLabel pageNumLabel = new JLabel(BundleUtil.getString("LBL_BookPageNum"));
        JLabel classifierLabel = new JLabel(BundleUtil.getString("LBL_BookClassifier"));
        JLabel summaryLabel = new JLabel(BundleUtil.getString("LBL_BookSummary"));
        JLabel remarksLabel = new JLabel(BundleUtil.getString("LBL_BookRemarks"));
        JScrollPane summaryScrollPane = new JScrollPane(summaryTextArea_);
        JScrollPane remarksScrollPane = new JScrollPane(remarksTextArea_);
        JButton okButton = new JButton(new ApplyAction());
        JButton cancelButton = new JButton(new CancelAction());

        JPanel dialogPane = new JPanel();
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        dialogPane.setBorder(Borders.DIALOG_BORDER);
        dialogPane.setLayout(new BorderLayout());

        CellConstraints cc = new CellConstraints();
        FormLayout layout = new FormLayout(
                "default, $lcgap, 70dlu:grow, " +
                        "$rgap, default, $lcgap, 70dlu:grow, " +
                        "$ugap, default, $lcgap, 50dlu:grow",
                "6*(default, $lgap), 20dlu:grow, $lgap, default, $lgap, 30dlu:grow"
        );
        PanelBuilder builder = new PanelBuilder(layout);
        builder.add(barCodeLabel, cc.xy(1, 1));
        builder.add(barCodeTextField_, cc.xy(3, 1));
        builder.add(isbnLabel, cc.xy(5, 1));
        builder.add(isbnTextField_, cc.xy(7, 1));
        builder.add(authorLabel, cc.xy(9, 1));
        builder.add(authorTextField_, cc.xy(11, 1));
        builder.add(nameLabel, cc.xy(1, 3));
        builder.add(nameTextField_, cc.xyw(3, 3, 5));
        builder.add(translatorLabel, cc.xy(9, 3));
        builder.add(translatorTextField_, cc.xy(11, 3));
        builder.add(publisherLabel, cc.xy(1, 5));
        builder.add(publisherTextField_, cc.xyw(3, 5, 5));
        builder.add(priceLabel, cc.xy(9, 5));
        builder.add(priceSpinner_, cc.xy(11, 5));
        builder.add(pubDateLabel, cc.xy(1, 7));
        builder.add(pubDatePicker_, cc.xy(3, 7));
        builder.add(callNumLabel, cc.xy(5, 7));
        builder.add(callNumTextField_, cc.xy(7, 7));
        builder.add(editionLabel, cc.xy(9, 7));
        builder.add(editionSpinner_, cc.xy(11, 7));
        builder.add(seriesLabel, cc.xy(1, 9));
        builder.add(seriesTextField_, cc.xy(3, 9));
        builder.add(volumeLabel, cc.xy(5, 9));
        builder.add(volumeTextField_, cc.xy(7, 9));
        builder.add(pageNumLabel, cc.xy(9, 9));
        builder.add(pageNumSpinner_, cc.xy(11, 9));
        builder.add(summaryLabel, cc.xy(1, 11));
        builder.add(summaryScrollPane, cc.xywh(3, 11, 5, 3));
        builder.add(classifierLabel, cc.xy(9, 11));
        builder.add(classifierTextField_, cc.xy(11, 11));
        builder.add(remarksLabel, cc.xy(1, 15));
        builder.add(remarksScrollPane, cc.xywh(3, 15, 5, 3));
        JPanel contentPanel = builder.getPanel();
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
        setModal(true);
        pack();
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        initWidgetStatus();
    }

    /**
     * Init widget status, such as enable or disable a widget.
     */
    protected abstract void initWidgetStatus();

    /**
     * Commit the book data, such as add new book or update book.
     *
     * @return true if commit book successfully
     */
    protected abstract boolean commitBook();

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
                            AbstractBookDialog.this, result.getMessagesText(),
                            BundleUtil.getString("TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                }
                return;
            }
            BookBean bookBean = bindingModel_.getBean();
            bookBean.setPubDate(pubDatePicker_.getDate());
            bookBean.setPrice((Double) priceSpinner_.getValue());
            bookBean.setEdition((Integer) editionSpinner_.getValue());
            bookBean.setPageNum((Integer) pageNumSpinner_.getValue());
            if (commitBook()) {
                AbstractBookDialog.this.dispose();
            }
        }
    }

    private final class CancelAction extends AbstractAction {

        private CancelAction() {
            super(BundleUtil.getString("BTN_Cancel"));
        }

        public void actionPerformed(ActionEvent e) {
            AbstractBookDialog.this.dispose();
        }
    }
}
