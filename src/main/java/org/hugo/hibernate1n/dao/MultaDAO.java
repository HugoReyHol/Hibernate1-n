package org.hugo.hibernate1n.dao;

import org.hibernate.Session;
import org.hugo.hibernate1n.model.Coche;
import org.hugo.hibernate1n.model.Multa;

import java.util.List;


public interface MultaDAO {
    boolean guardar(Multa multa, Session session);

    void actualizar(Multa multa, Session session);

    void eliminar(Multa multa, Session session);

    List<Multa> listar(Coche coche, Session session);
}
