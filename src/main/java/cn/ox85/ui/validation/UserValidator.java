package cn.ox85.ui.validation;

import cn.ox85.ui.beans.UserBean;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.SimpleValidationSupport;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * @author alec zhang
 */
public class UserValidator implements Validator<UserBean> {
    @Override
    public ValidationResult validate(UserBean userBean) {
        SimpleValidationSupport support = new SimpleValidationSupport();
        if (ValidationUtils.isBlank(userBean.getUsername())) {
            support.addError(UserBean.PROPERTYNAME_USERNAME,
                    BundleUtil.getString("MSG_NoBlankUserName"));
        }
        String password = userBean.getPassword();
        String confirmPass = userBean.getConfirmpass();
        if (password == null) {
            support.addError(UserBean.PROPERTYNAME_PASSWORD,
                    BundleUtil.getString("MSG_NoBlankPassword"));
        }
        if (confirmPass != null && !confirmPass.equals(password)) {
            support.addError(UserBean.PROPERTYNAME_CONFIRMPASS,
                    BundleUtil.getString("MSG_NoEqualPassword"));
        }
        return support.getResult();
    }
}
