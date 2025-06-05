package Bd.dao;

import org.example.model.Comentariu;

public class ComentariuDao extends GenericDao<Comentariu> {
    private static final ComentariuDao INSTANCE = new ComentariuDao();
    private ComentariuDao() {}
    public static ComentariuDao getInstance() { return INSTANCE; }
}
