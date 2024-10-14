package com.online_restaurant.backend.repository;


import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.model.Like;
import com.online_restaurant.backend.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LikeRepo {

    @Autowired
    private EntityManagerFactory emf;

    public void doLike(Like like){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(like);
        em.getTransaction().commit();

    }


    public int unLike(Food food, User user){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        int result = em.createQuery("remove from Like like where  like.user= :user and like.food :food").
                setParameter("user",user).setParameter("food",food).executeUpdate();
        em.getTransaction().commit();
        return result;
    }

}
