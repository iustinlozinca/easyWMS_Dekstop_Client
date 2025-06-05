package Bd.dao;

import Bd.util.AuditService;
import Bd.util.DbUtil;
import org.example.model.ProdusComanda;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class ProdusComandaDao {
    private static final ProdusComandaDao INSTANCE = new ProdusComandaDao();
    private ProdusComandaDao() {}
    public static ProdusComandaDao getInstance() { return INSTANCE; }

    public ProdusComanda create(ProdusComanda pc) {
        String sql = "INSERT INTO Produs_Comanda(produsId, comandaId, cantitateComandata, cantitateScanata) VALUES (?,?,?,?)";
        try (Connection conn = DbUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pc.getProdusId());
            ps.setInt(2, pc.getComandaId());
            ps.setInt(3, pc.getCantitateComandata());
            ps.setInt(4, pc.getCantitateScanata());
            ps.executeUpdate();
            AuditService.getInstance().log("create_ProdusComanda");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pc;
    }

    public List<ProdusComanda> findByComandaId(Integer comandaId) {
        List<ProdusComanda> list = new ArrayList<>();
        String sql = "SELECT * FROM Produs_Comanda WHERE comandaId=?";
        try (Connection conn = DbUtil.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, comandaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ProdusComanda pc = new ProdusComanda();
                    pc.setProdusId(rs.getInt("produsId"));
                    pc.setComandaId(rs.getInt("comandaId"));
                    pc.setCantitateComandata(rs.getInt("cantitateComandata"));
                    pc.setCantitateScanata(rs.getInt("cantitateScanata"));
                    list.add(pc);
                }
            }
            AuditService.getInstance().log("find_ProdusComanda");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
