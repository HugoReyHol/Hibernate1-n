package org.hugo.hibernate1n.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hugo.hibernate1n.model.Coche;
import org.hugo.hibernate1n.util.AlertUtil;

import java.util.ArrayList;
import java.util.List;


public class CocheDAOImpl implements CocheDAO {

    public boolean guardarCoche(Coche coche, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(coche);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();

            AlertUtil.mostrarError(e.getCause().getMessage());

            return false;
        }

        return true;
    }

    public void actualizarCoche(Coche coche, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(coche);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();

            AlertUtil.mostrarError(e.getCause().getMessage());

        }
    }

    public void eliminarCoche(Coche coche, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.delete(coche);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();

        }
    }

    @SuppressWarnings("unchecked")
    public List<Coche> listarCoches(Session session) {
        Transaction transaction = null;
        List<Coche> coches = new ArrayList<>();

        try {
            transaction = session.beginTransaction();
            coches = session.createQuery("from Coche").list();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
        }

        return coches;
    }
}
