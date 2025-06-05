package Bd.dao;

import org.example.model.User;

public class UserDao extends GenericDao<User> {
    private static final UserDao INSTANCE = new UserDao();
    private UserDao() {}
    public static UserDao getInstance() { return INSTANCE; }
}
