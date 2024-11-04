package org.hugo.hibernate1n.dao;

import org.hibernate.Session;
import org.hugo.hibernate1n.model.Coche;

import java.util.List;


public interface CocheDAO {

    boolean guardarCoche(Coche coche, Session session);

    void actualizarCoche(Coche coche, Session session);

    void eliminarCoche(Coche coche, Session session);

    List<Coche> listarCoches(Session session);

}
