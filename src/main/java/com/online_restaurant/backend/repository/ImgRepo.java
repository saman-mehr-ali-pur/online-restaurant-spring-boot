package com.online_restaurant.backend.repository;

import com.online_restaurant.backend.ioUtil.ImageIo;
import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.model.Image;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ImgRepo {

    @Autowired
    private EntityManagerFactory emf;

    @Autowired
    private ImageIo imageIo;

    @Value("${root-path}")
    private String root;


    public List<Image> getImageByFood(Food food){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        List<Image> reuslt = em.createQuery("select img from Image img where img.food = :food",Image.class).
            setParameter("food",food)
                .getResultList();
        em.getTransaction().commit();

        return reuslt;
    }


    public boolean saveImage(byte[] imgBytes, Food food,String sufix){

        String randomName = UUID.randomUUID().toString();
        String fullName = randomName.concat(".".concat(sufix));
        String dirPath = root.concat("/restaurant");
        Image image = new Image();
        image.setFood(food);
        image.setPath(dirPath+fullName);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(image);
        em.getTransaction().commit();

        try {
            return  imageIo.saveImage(fullName,dirPath,imgBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
