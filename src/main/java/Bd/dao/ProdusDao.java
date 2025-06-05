package Bd.dao;

import org.example.model.Produs;

public class ProdusDao extends GenericDao<Produs> {
    private static final ProdusDao INSTANCE = new ProdusDao();
    private ProdusDao() {}
    public static ProdusDao getInstance() { return INSTANCE; }
}
