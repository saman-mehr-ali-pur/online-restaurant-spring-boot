package com.online_restaurant.backend.repository;


import com.online_restaurant.backend.model.Comment;
import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.model.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepo {

    @Autowired
    private EntityManagerFactory emf;

    public Comment saveComment(Comment comment){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(comment);
        em.getTransaction().commit();
        return comment;
    }


    public List<Comment> getAll(Food food ){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Comment> reuslt = em.createQuery("select c from Comment as c where c.food.id= :foodid").
                setParameter("foodid",food.getId())
                .getResultList();

        em.getTransaction().commit();

        return  reuslt;
    }


    public Comment getById(Comment comment){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Comment result = em.find(Comment.class,comment.getId());
        em.getTransaction().commit();

        return result;
    }



    public void update(Comment comment){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Comment com = em.find(Comment.class,comment.getId());
        com.setComment(comment.getComment());
        em.getTransaction().commit();
//        return true;
    }


    public void delete(Comment comment ){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.remove(comment);
        em.getTransaction().commit();

    }

}
