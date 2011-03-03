package cn.ox85.ui.components;

import cn.ox85.sql.ConnectionFactory;
import cn.ox85.ui.beans.LoginBean;
import cn.ox85.ui.binding.BindingModel;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.ButtonBarBuilder;
import cn.ox85.ui.util.IconLoader;
import cn.ox85.ui.validation.LoginValidator;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.view.ValidationComponentUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author alec zhang
 */
public class LoginFrame extends JFrame {
    private static final Logger logger_ = LoggerFactory.getLogger(LoginFrame.class);

    private JTextField userTextField_;
    private JPasswordField passwordField_;
    private final BindingModel<LoginBean> bindingModel_ =
            new BindingModel<LoginBean>(new LoginBean(), new LoginValidator());

    public LoginFrame() {
        new TempConnectionWork().execute();
        buildPanel();
    }

    private void initComponents() {
        userTextField_ = BasicComponentFactory.createTextField(
                bindingModel_.getBufferedModel(LoginBean.PROPERTYNAME_USERNAME));
        passwordField_ = BasicComponentFactory.createPasswordField(
                bindingModel_.getBufferedModel(LoginBean.PROPERTYNAME_PASSWORD), false);
    }

    private void initComponentAnnotations() {
        ValidationComponentUtils.setMandatory(userTextField_, true);
        ValidationComponentUtils.setMessageKey(userTextField_, LoginBean.PROPERTYNAME_USERNAME);
        ValidationComponentUtils.setMandatory(passwordField_, true);
        ValidationComponentUtils.setMessageKey(passwordField_, LoginBean.PROPERTYNAME_PASSWORD);
    }

    private void initEventHandling() {
        KeyListener enterListener = new EnterKeyAdapter();
        userTextField_.addKeyListener(enterListener);
        passwordField_.addKeyListener(enterListener);
    }

    private void buildPanel() {
        initComponents();
        initComponentAnnotations();
        initEventHandling();

        JLabel loginLabel = new JLabel(IconLoader.getIcon("login.png"));
        JLabel userLabel = new JLabel(BundleUtil.getString("LBL_User"));
        JLabel passwordLabel = new JLabel(BundleUtil.getString("LBL_Pass"));
        JButton loginButton = new JButton(new LoginAction());
        JButton cancelButton = new JButton(new CancelAction());

        CellConstraints cc = new CellConstraints();
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(loginLabel, BorderLayout.NORTH);

        JPanel dialogPane = new JPanel();
        dialogPane.setBorder(Borders.DIALOG_BORDER);
        dialogPane.setLayout(new BorderLayout());

        FormLayout layout = new FormLayout(
                "default, $lcgap, default:grow",
                "default, $lgap, default, $lgap");
        PanelBuilder pbuilder = new PanelBuilder(layout);
        pbuilder.add(userLabel, cc.xy(1, 1));
        pbuilder.add(userTextField_, cc.xy(3, 1));
        pbuilder.add(passwordLabel, cc.xy(1, 3));
        pbuilder.add(passwordField_, cc.xy(3, 3));
        dialogPane.add(bindingModel_.getIconFeedbackPanel(pbuilder.getPanel()), BorderLayout.CENTER);

        ButtonBarBuilder bbuillder = new ButtonBarBuilder();
        bbuillder.setBorder(Borders.BUTTON_BAR_GAP_BORDER);
        bbuillder.addGlue();
        bbuillder.addButton(loginButton);
        bbuillder.addRelatedGap();
        bbuillder.addButton(cancelButton);
        dialogPane.add(bbuillder.getPanel(), BorderLayout.SOUTH);

        contentPane.add(dialogPane, BorderLayout.CENTER);

        setTitle(BundleUtil.getString("TITLE_Login"));
        setIconImage(IconLoader.getIcon32("main.png").getImage());
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private static void openMainFrame() {
        MainFrame frame = new MainFrame();
        Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment()
                .getMaximumWindowBounds();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(r.width / 2 + 300, r.height / 2 + 200));
        frame.setMinimumSize(new Dimension(r.width / 10, r.height / 2));
        frame.pack();
        frame.setLocation(r.x, r.y);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void login() {
        bindingModel_.triggerCommit();
        if (bindingModel_.hasValidationErrors() || bindingModel_.hasValidationWarnings()) {
            // Handle unknown errors when validate
            ValidationResult result = bindingModel_.getUnexpectedResult();
            if (result != null) {
                JOptionPane.showMessageDialog(
                        this, result.getMessagesText(),
                        BundleUtil.getString("TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
            }
            return;
        }
        LoginFrame.this.dispose();
        openMainFrame();
    }

    private final class LoginAction extends AbstractAction {

        private LoginAction() {
            super(BundleUtil.getString("BTN_Login"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            login();
        }
    }

    private final class CancelAction extends AbstractAction {

        private CancelAction() {
            super(BundleUtil.getString("BTN_Cancel"));
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            LoginFrame.this.dispose();
        }
    }

    /**
     * Init an session in background, because the first time
     * init connection takes a little long.
     */
    private static class TempConnectionWork extends SwingWorker<Object, Object> {

        @Override
        protected Object doInBackground() throws Exception {
            SqlSession session = ConnectionFactory.getSession().openSession();
            session.close();
            return null;
        }
    }

    private class EnterKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (userTextField_ == e.getSource()) {
                    passwordField_.grabFocus();
                } else if (passwordField_ == e.getSource()) {
                    login();
                }
            }
        }
    }
}