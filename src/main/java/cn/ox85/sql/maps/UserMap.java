package cn.ox85.sql.maps;

import cn.ox85.models.User;

import java.util.List;

/**
 * @author alec zhang
 */
public interface UserMap {
    public int insertUser(User user);

    public int updateUser(User user);

    public int deleteUsersById(List<Integer> ids);

    public User selectUserById(int id);

    public User selectUserByName(String username);

    public List<User> selectUsers();
}
