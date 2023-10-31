package com.shop.hibernateControllers;

import com.shop.classes.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class GenericHib {
    private EntityManagerFactory entityManagerFactory;
    public GenericHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }

    public <T> void create(T entity){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e){
            System.err.println("Error in creating user in database");
        } finally {
            if (em != null){
                em.close();
            }
        }
    }
}
