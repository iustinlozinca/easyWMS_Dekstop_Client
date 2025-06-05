package Bd.dao;

import Bd.util.AuditService;
import Bd.util.DbUtil;
import org.example.model.UserComanda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserComandaDao {
    private static final UserComandaDao INSTANCE = new UserComandaDao();
    private UserComandaDao() {}
    public static UserComandaDao getInstance() { return INSTANCE; }

    public void create(UserComanda uc) {
        String sql = "INSERT INTO User_Comanda(userId, comandaId) VALUES (?,?)";
        try (Connection conn = DbUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, uc.getUserId());
            ps.setInt(2, uc.getComandaId());
            ps.executeUpdate();
            AuditService.getInstance().log("create_UserComanda");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
