package hibernateControllers;

import com.shop.classes.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class UserHib {
    private EntityManagerFactory entityManagerFactory;
    public UserHib(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    private EntityManager getEntityManager(){
        return entityManagerFactory.createEntityManager();
    }

    public void createUser(User user){
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(user);
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
