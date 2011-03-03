package cn.ox85.ui.components.user;

import cn.ox85.models.User;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.UserMap;
import cn.ox85.ui.beans.UserBean;
import cn.ox85.ui.binding.BindingModel;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.IconLoader;
import cn.ox85.ui.validation.UserValidator;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * @author alec zhang
 */
public class UserEditDialog extends AbstractUserDialog {
    private static final Logger logger_ = LoggerFactory.getLogger(UserEditDialog.class);

    public UserEditDialog(Dialog owner, int userId) {
        super(owner);
        init(userId);
    }

    private void init(int userId) {
        setIconImage(IconLoader.getIcon32("main.png").getImage());
        setTitle(BundleUtil.getString("TITLE_UserEdit"));
        SqlSession session = ConnectionFactory.getSession().openSession();
        User user;
        try {
            UserMap userDAO = session.getMapper(UserMap.class);
            user = userDAO.selectUserById(userId);
        } finally {
            session.close();
        }
        //Super admin is not allowed to change user type
        if (User.SUPER_ADMIN.equals(user.getUsername())) {
            userTypeComboBox_.setEnabled(false);
        }
        bindingModel_.setBean(new UserBean(user));
    }

    @Override
    protected BindingModel<UserBean> initBindingModel() {
        return new BindingModel<UserBean>(new UserBean(), new UserValidator());
    }

    @Override
    protected void initWidgetStatus() {
        usernameTextField_.setEditable(false);
    }

    @Override
    protected boolean commitUser() {
        UserBean userModel = bindingModel_.getBean();
        try {
            return 1 == userModel.updateUser();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this, "Fail to edit user.",
                    BundleUtil.getString("TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
            logger_.error("Fail to edit user.", e);
            return false;
        }
    }
}
