/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sv.udb.controladores;

import com.sv.udb.modelos.Libros;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author bernardo
 */
@Stateless
public class LibrosFacade extends AbstractFacade<Libros> implements LibrosFacadeLocal {

    @PersistenceContext(unitName = "POOPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LibrosFacade() {
        super(Libros.class);
    }

    @Override
    public List<Libros> findByEstaLibr() {
        TypedQuery<Libros> query = em.createNamedQuery("Libros.findByEstaLibr", Libros.class);
        query.setParameter("estaLibr", 1);
        return query.getResultList();
    }
    
}
