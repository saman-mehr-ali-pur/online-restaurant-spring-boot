package com.online_restaurant.backend.service;

import com.online_restaurant.backend.model.Comment;
import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    public List<Comment> getAll(Food food,int limit){
        return commentRepo.getAllCommentByFood(food,limit);
    }


    public Comment save( Comment comment){
        return commentRepo.saveComment(comment.getUser(),comment.getFood(),comment);
    }


    public boolean deleteComment(Comment comment){
        return commentRepo.deleteComment(comment);
    }

}
