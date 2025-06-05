package Bd.dao;

import org.example.model.Comanda;

public class ComandaDao extends GenericDao<Comanda> {
    private static final ComandaDao INSTANCE = new ComandaDao();
    private ComandaDao() {}
    public static ComandaDao getInstance() { return INSTANCE; }
}
