package cn.ox85.models;

import java.util.Date;

/**
 * @author alec zhang
 */
public class User {
    public static final String SUPER_ADMIN = "admin";
    public static final int USER_NORMAL = 0;
    public static final int USER_ADMIN = 1;

    private int id;
    private String username;
    private String password;
    private int type = USER_NORMAL;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
