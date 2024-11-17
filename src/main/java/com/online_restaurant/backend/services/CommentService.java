package com.online_restaurant.backend.services;

import com.online_restaurant.backend.model.Comment;
import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    public Comment add(Comment comment){
        commentRepo.saveComment(comment);
        return comment;
    }


    public Comment update(Comment comment){
        commentRepo.update(comment);
        return comment;
    }


    public boolean delete(Comment comment){
        commentRepo.delete(comment);
        return true;
    }

    public List<Comment> getByfood(Food food){
        return commentRepo.getAll(food);
    }
}
