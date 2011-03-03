package cn.ox85.ui.components.user;

import cn.ox85.ui.beans.UserBean;
import cn.ox85.ui.binding.BindingModel;
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * @author alec zhang
 */
public abstract class AbstractUserDialog extends JDialog {
    protected JTextField usernameTextField_;
    protected JPasswordField userpasswordField_;
    protected JComboBox userTypeComboBox_;
    protected JPasswordField confirmPasswordField_;

    protected final BindingModel<UserBean> bindingModel_;

    public AbstractUserDialog(Dialog owner) {
        super(owner);
        bindingModel_ = initBindingModel();
        buildPanel();
    }

    protected abstract BindingModel<UserBean> initBindingModel();

    private void initComponents() {
        usernameTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(UserBean.PROPERTYNAME_USERNAME));
        userpasswordField_ = BasicComponentFactory.createPasswordField(
                bindingModel_.getBufferedModel(UserBean.PROPERTYNAME_PASSWORD));
        ValueModel userTypeModel = bindingModel_.getBufferedModel(UserBean.PROPERTYNAME_USERTYPE);
        userTypeComboBox_ = BasicComponentFactory.createComboBox(
                new SelectionInList<UserBean.Type>(UserBean.TYPE_CHOICES, userTypeModel));
        confirmPasswordField_ = BasicComponentFactory.createPasswordField(
                bindingModel_.getBufferedModel(UserBean.PROPERTYNAME_CONFIRMPASS));
    }

    private void initComponentAnnotations() {
        ValidationComponentUtils.setMandatory(usernameTextField_, true);
        ValidationComponentUtils.setMessageKey(usernameTextField_, UserBean.PROPERTYNAME_USERNAME);
        ValidationComponentUtils.setMandatory(userpasswordField_, true);
        ValidationComponentUtils.setMessageKey(userpasswordField_, UserBean.PROPERTYNAME_PASSWORD);
        ValidationComponentUtils.setMandatory(confirmPasswordField_, true);
        ValidationComponentUtils.setMessageKey(confirmPasswordField_, UserBean.PROPERTYNAME_CONFIRMPASS);
    }

    /**
     * Init widget status, such as enable or disable a widget.
     */
    protected abstract void initWidgetStatus();

    private void buildPanel() {
        initComponents();
        initComponentAnnotations();
        initWidgetStatus();

        JPanel dialogPane = new JPanel();
        JLabel usernameLabel = new JLabel(BundleUtil.getString("LBL_Username"));
        JLabel passwordLabel = new JLabel(BundleUtil.getString("LBL_UserPassword"));
        JLabel userTypeLabel = new JLabel(BundleUtil.getString("LBL_UserType"));
        JLabel passConfirmLabel = new JLabel(BundleUtil.getString("LBL_UserConfirmPass"));
        JButton okButton = new JButton(new ApplyAction());
        JButton cancelButton = new JButton(new CancelAction());
        CellConstraints cc = new CellConstraints();

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        dialogPane.setBorder(Borders.DIALOG_BORDER);
        dialogPane.setLayout(new BorderLayout());

        FormLayout layout = new FormLayout(
                "default, $lcgap, 90dlu:grow",
                "3*(default, $lgap), default");
        PanelBuilder builder = new PanelBuilder(layout);
        builder.add(usernameLabel, cc.xy(1, 1));
        builder.add(usernameTextField_, cc.xy(3, 1));
        builder.add(userTypeLabel, cc.xy(1, 3));
        builder.add(userTypeComboBox_, cc.xy(3, 3));
        builder.add(passwordLabel, cc.xy(1, 5));
        builder.add(userpasswordField_, cc.xy(3, 5));
        builder.add(passConfirmLabel, cc.xy(1, 7));
        builder.add(confirmPasswordField_, cc.xy(3, 7));
        dialogPane.add(bindingModel_.getIconFeedbackPanel(builder.getPanel()), BorderLayout.CENTER);

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
    }

    protected abstract boolean commitUser();

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
                            AbstractUserDialog.this, result.getMessagesText(),
                            BundleUtil.getString("TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
                }
                return;
            }
            if (commitUser()) {
                AbstractUserDialog.this.dispose();
                Window owner = getOwner();
                if (owner instanceof UserManageDialog) {
                    ((UserManageDialog) owner).updateUserTable();
                }
            }
        }
    }

    private final class CancelAction extends AbstractAction {

        private CancelAction() {
            super(BundleUtil.getString("BTN_Cancel"));
        }

        public void actionPerformed(ActionEvent e) {
            AbstractUserDialog.this.dispose();
        }
    }
}
