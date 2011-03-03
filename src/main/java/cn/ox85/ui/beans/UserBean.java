package cn.ox85.ui.beans;

import cn.ox85.models.User;
import cn.ox85.security.MD5Util;
import cn.ox85.sql.ConnectionFactory;
import cn.ox85.sql.maps.UserMap;
import cn.ox85.ui.util.BundleUtil;
import com.jgoodies.binding.beans.Model;
import org.apache.ibatis.session.SqlSession;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * @author alec zhang
 */
public class UserBean extends Model {
    public static final String PROPERTYNAME_ID = "id";
    public static final String PROPERTYNAME_USERNAME = "username";
    public static final String PROPERTYNAME_PASSWORD = "password";
    public static final String PROPERTYNAME_CONFIRMPASS = "confirmpass";
    public static final String PROPERTYNAME_USERTYPE = "type";
    public static final String PROPERTYNAME_DATE = "date";

    public static final String PASSSHOW = "1111";
    public static final java.util.List<Type> TYPE_CHOICES =
            Collections.unmodifiableList(Arrays.asList(Type.OPERATOR, Type.ADMIN, Type.SUPERADMIN));

    private User user_;
    private String password_;
    private String confirmpass_;

    public UserBean() {
        user_ = new User();
    }

    public UserBean(User user) {
        user_ = user;
        password_ = PASSSHOW;
        confirmpass_ = PASSSHOW;
    }

    public int insertUser() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        SqlSession session = ConnectionFactory.getSession().openSession();
        int count = 0;
        try {
            UserMap userDAO = session.getMapper(UserMap.class);
            user_.setPassword(MD5Util.MD5(password_));
            count = userDAO.insertUser(user_);
            session.commit();
        } finally {
            session.close();
        }
        return count;
    }

    public int updateUser() throws NoSuchAlgorithmException, UnsupportedEncodingException {
        SqlSession session = ConnectionFactory.getSession().openSession();
        int count = 0;
        try {
            UserMap userDAO = session.getMapper(UserMap.class);
            if (password_ != PASSSHOW) {
                user_.setPassword(MD5Util.MD5(password_));
            }
            count = userDAO.updateUser(user_);
            session.commit();
        } finally {
            session.close();
        }
        return count;
    }

    public int getId() {
        return user_.getId();
    }

    public void setId(int id) {
        int old = getId();
        user_.setId(id);
        firePropertyChange(PROPERTYNAME_ID, old, id);
    }

    public String getUsername() {
        return user_.getUsername();
    }

    public void setUsername(String username) {
        String old = getUsername();
        user_.setUsername(username);
        firePropertyChange(PROPERTYNAME_USERNAME, old, username);
    }

    public String getPassword() {
        return password_;
    }

    public void setPassword(String password) {
        String old = getPassword();
        password_ = password;
        firePropertyChange(PROPERTYNAME_PASSWORD, old, password);
    }

    public String getConfirmpass() {
        return confirmpass_;
    }

    public void setConfirmpass(String confirmpass) {
        String old = getConfirmpass();
        this.confirmpass_ = confirmpass;
        firePropertyChange(PROPERTYNAME_CONFIRMPASS, old, confirmpass);
    }

    public Type getType() {
        return Type.values()[user_.getType()];
    }

    public void setType(Type type) {
        Type old = getType();
        user_.setType(type.ordinal());
        firePropertyChange(PROPERTYNAME_USERTYPE, old, type);
    }

    public Date getDate() {
        return user_.getDate();
    }

    public void setDate(Date date) {
        Date old = getDate();
        user_.setDate(date);
        firePropertyChange(PROPERTYNAME_DATE, old, date);
    }

    public static enum Type {
        OPERATOR(BundleUtil.getString("ITEM_Operator")),
        ADMIN(BundleUtil.getString("ITEM_Admin")),
        SUPERADMIN(BundleUtil.getString("ITEM_SuperAdmin"));

        private String name_;

        private Type(String name) {
            name_ = name;
        }

        @Override
        public String toString() {
            return name_;
        }
    }
}
