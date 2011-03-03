package cn.ox85.ui.components.user;

import cn.ox85.ui.beans.UserBean;
import cn.ox85.ui.binding.BindingModel;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.IconLoader;
import cn.ox85.ui.validation.UserAddValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

/**
 * @author alec zhang
 */
public class UserAddDialog extends AbstractUserDialog {
    private static final Logger logger_ = LoggerFactory.getLogger(UserAddDialog.class);

    public UserAddDialog(Dialog owner) {
        super(owner);
        setIconImage(IconLoader.getIcon32("main.png").getImage());
        setTitle(BundleUtil.getString("TITLE_UserAdd"));
    }

    @Override
    protected BindingModel<UserBean> initBindingModel() {
        return new BindingModel<UserBean>(new UserBean(), new UserAddValidator());
    }

    @Override
    protected void initWidgetStatus() {
        //Do nothing
    }

    @Override
    protected boolean commitUser() {
        UserBean userModel = bindingModel_.getBean();
        try {
            return 1 == userModel.insertUser();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this, "Fail to add user.",
                    BundleUtil.getString("TITLE_ERROR"), JOptionPane.ERROR_MESSAGE);
            logger_.error("Fail to add user.", e);
            return false;
        }
    }
}
