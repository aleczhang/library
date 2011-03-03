package cn.ox85.ui.beans;

import cn.ox85.models.User;
import com.jgoodies.binding.beans.Model;

/**
 * @author alec zhang
 */
public class LoginBean extends Model {
    public static final String PROPERTYNAME_USERNAME = "username";
    public static final String PROPERTYNAME_PASSWORD = "password";

    private static User currentUser_;
    private String username;
    private String password;

    public static User getCurrentUser() {
        return currentUser_;
    }

    public static void setCurrentUser(User user) {
        currentUser_ = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        String old = getUsername();
        this.username = username;
        firePropertyChange(PROPERTYNAME_USERNAME, old, username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String old = getPassword();
        this.password = password;
        firePropertyChange(PROPERTYNAME_PASSWORD, old, password);
    }
}
