package org.hugo.hibernate1n.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hugo.hibernate1n.model.Coche;
import org.hugo.hibernate1n.model.Multa;
import org.hugo.hibernate1n.util.AlertUtil;

import java.util.ArrayList;
import java.util.List;

public class MultaDAOImpl implements DAO<Multa>{
    @Override
    public boolean guardar(Multa elemento, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(elemento);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();

            AlertUtil.mostrarError(e.getCause().getMessage());

            return false;

        }

        return true;
    }

    @Override
    public void actualizar(Multa elemento, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(elemento);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();

            AlertUtil.mostrarError(e.getCause().getMessage());

        }
    }

    @Override
    public void eliminar(Multa elemento, Session session) {
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Coche c = session.get(Coche.class, elemento.getId_multa());
            session.delete(c);
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();

        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Multa> listar(Session session) {
        Transaction transaction = null;
        List<Multa> multas = new ArrayList<>();

        try {
            transaction = session.beginTransaction();
            multas = session.createQuery("from Multa").list();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
        }

        return multas;
    }

    @SuppressWarnings("unchecked")
    public List<Multa> listar(Coche elemento, Session session) {
        Transaction transaction = null;
        List<Multa> multas = new ArrayList<>();

        try {
            transaction = session.beginTransaction();
            multas = session.createQuery("from Multa where matricula = \'" + elemento.getMatricula() + "\'").list();
            transaction.commit();

        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
        }

        return multas;
    }
}