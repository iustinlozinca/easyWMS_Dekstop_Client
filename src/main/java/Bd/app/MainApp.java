package Bd.app;

import Bd.dao.*;
import Bd.util.DbUtil;
import org.example.model.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        try (Connection conn = DbUtil.getConnection()) {
            if (conn == null) {
                System.err.println("Nu s-a putut obtine conexiunea");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        User user = new User("Ion", "ADMIN");
        UserDao.getInstance().create(user);

        Produs produs = new Produs("1234567890123", "Produs Test", "CI1", 1, 100);
        ProdusDao.getInstance().create(produs);

        Comanda comanda = new Comanda();
        comanda.setDataComanda(LocalDateTime.now());
        comanda.setStatus("NOU");
        comanda.setValoareTotala(BigDecimal.ZERO);
        ComandaDao.getInstance().create(comanda);

        UserComandaDao.getInstance().create(new UserComanda(user.getId(), comanda.getId()));
        UserComandaDao.getInstance().create(new UserComanda(user.getId(), comanda.getId()));

        ProdusComanda pc1 = new ProdusComanda("835215213", "test", "ceva codIntern", 2,30, 20);
        pc1.setProdusId(produs.getId());
        pc1.setComandaId(comanda.getId());
        pc1.setCantitateComandata(5);
        ProdusComandaDao.getInstance().create(pc1);

        ProdusComanda pc2 = new ProdusComanda("8315213", "t3est", "ceva c24odIntern", 2,330, 20);
        pc2.setProdusId(produs.getId());
        pc2.setComandaId(comanda.getId());
        pc2.setCantitateComandata(3);
        ProdusComandaDao.getInstance().create(pc2);

        Comentariu comentariu = new Comentariu();
        comentariu.setUserId(user.getId());
        comentariu.setComandaId(comanda.getId());
        comentariu.setData(LocalDateTime.now());
        comentariu.setComentariu("Comentariu test");
        ComentariuDao.getInstance().create(comentariu);

        List<Produs> produse = ProdusDao.getInstance().findAll(Produs.class);
        System.out.println("Produse: " + produse.size());

        List<Comanda> comenzi = ComandaDao.getInstance().findAll(Comanda.class);
        System.out.println("Comenzi: " + comenzi.size());

        System.out.println("Audit logged in audit.csv");
    }
}
