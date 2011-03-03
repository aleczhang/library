package cn.ox85.ui.validation;

import cn.ox85.models.User;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.UserMap;
import cn.ox85.ui.beans.UserBean;
import cn.ox85.ui.util.BundleUtil;
import cn.ox85.ui.util.SimpleValidationSupport;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.util.ValidationUtils;
import org.apache.ibatis.session.SqlSession;

/**
 * @author alec zhang
 */
public class UserAddValidator extends UserValidator {
    @Override
    public ValidationResult validate(UserBean userBean) {
        SimpleValidationSupport support = new SimpleValidationSupport(super.validate(userBean));
        String username = userBean.getUsername();
        if (!ValidationUtils.isBlank(username)) {
            SqlSession session = ConnectionFactory.getSession().openSession();
            try {
                UserMap userMap = session.getMapper(UserMap.class);
                User user = userMap.selectUserByName(username);
                if (user != null) {
                    support.addError(UserBean.PROPERTYNAME_USERNAME,
                            BundleUtil.getString("MSG_DuplicateUsername"));
                }
            } finally {
                session.close();
            }
        }
        return support.getResult();
    }
}
