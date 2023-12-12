package com.shop.hibernateControllers;

import com.shop.classes.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

public class UtilsHib {
    private EntityManagerFactory entityManagerFactory;
    public UtilsHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }

    public User getUserByCredentials(String username, String password){
        EntityManager em = null;
        try {
            em = getEntityManager();
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<User> query = cb.createQuery(User.class);
            Root<User> root = query.from(User.class);
            query.select(root).where(cb.and(cb.like(root.get("username"), username), cb.like(root.get("password"), password)));

            Query q = em.createQuery(query);
            return (User) q.getSingleResult();
        } catch (Exception e){
            System.out.println("Error in getting user by credentials");
            return null;
        } finally {
            if (em != null){
                em.close();
            }
        }
    }

    public <T> List<T> getAllRecords(Class<T> entityClass){
        EntityManager em = null;
        List<T> results = new ArrayList<>();
        try {
            em = getEntityManager();
            CriteriaQuery<Object> query = em.getCriteriaBuilder().createQuery();
            query.select(query.from(entityClass));
            Query q = em.createQuery(query);
            results = q.getResultList();
        } catch (Exception e){
            System.out.println("Error in getting all records.");
        } finally {
            if (em != null){
                em.close();
            }
        }
        return results;
    }

    public static <T> T getEntityById(EntityManagerFactory entityManagerFactory, Class<T> entityClass, long ID){
        T result = null;
        EntityManager em = null;
        try {
            em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
            result = em.find(entityClass, ID);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error in getting entity by ID");
        } finally {
            if (em != null){
                em.close();
            }
        }
        return result;
    }
}
