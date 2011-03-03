package cn.ox85.ui.validation;

import cn.ox85.models.User;
import cn.ox85.security.MD5Util;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.UserMap;
import cn.ox85.ui.beans.LoginBean;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.SimpleValidationSupport;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.Validator;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author alec zhang
 */
public class LoginValidator implements Validator<LoginBean> {
    private static final Logger logger_ = LoggerFactory.getLogger(LoginValidator.class);

    @Override
    public ValidationResult validate(LoginBean loginBean) {
        SimpleValidationSupport support =
                new SimpleValidationSupport();
        String username = loginBean.getUsername();
        String inputpass = loginBean.getPassword();
        SqlSession session = ConnectionFactory.getSession().openSession();
        String password;
        User user;
        try {
            UserMap userMap = session.getMapper(UserMap.class);
            user = userMap.selectUserByName(username);
            if (user == null) {
                support.addError(LoginBean.PROPERTYNAME_USERNAME, BundleUtil.getString("MSG_InvalidUser"));
                return support.getResult();
            } else {
                password = user.getPassword();
            }
        } finally {
            session.close();
        }

        if (password != null) {
            try {
                String md5pass = MD5Util.MD5(inputpass == null ? "" : inputpass);
                if (!md5pass.equals(password)) {
                    support.addError(LoginBean.PROPERTYNAME_PASSWORD, BundleUtil.getString("MSG_InvalidPass"));
                } else {
                    LoginBean.setCurrentUser(user);
                }
            } catch (Exception e1) {
                support.addError(null, String.valueOf(e1.getMessage()));
                logger_.error("Exception is thrown when sign password.", e1);
            }
        }
        return support.getResult();
    }
}
