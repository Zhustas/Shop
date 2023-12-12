package com.shop.hibernateControllers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class CRUDHib {
    private EntityManagerFactory entityManagerFactory;
    public CRUDHib(EntityManagerFactory entityManagerFactory) {
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
            System.out.println(entity);
        } catch (Exception e){
            System.out.println("Error in creating row in database");
        } finally {
            if (em != null){
                em.close();
            }
        }
    }

    public <T> T createAndReturn(T entity){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Error in creating row in database");
        } finally {
            if (em != null){
                em.close();
            }
        }
        return entity;
    }

    public <T> void update(T entity){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Error in updating row in database");
        } finally {
            if (em != null){
                em.close();
            }
        }
    }

    public <T> void delete(Class<T> entityClass, long ID){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            var object = em.find(entityClass, ID);
            em.remove(object);
            em.getTransaction().commit();
        } catch (Exception e){
            System.out.println("Error in deleting row in database");
        } finally {
            if (em != null){
                em.close();
            }
        }
    }
}
