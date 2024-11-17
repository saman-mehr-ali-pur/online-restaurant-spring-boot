package com.online_restaurant.backend.controller;

import com.online_restaurant.backend.model.Comment;
import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    private CommentService commentService;

    @PostMapping("/save")
    public Comment save(@RequestBody Comment comment){
        commentService.add(comment);
        return comment;
    }


    @PatchMapping("/update")
    public Comment update(Comment comment){
        commentService.update(comment);
        return comment;
    }

    @DeleteMapping("/delete/{id}")
    public boolean delete(@PathVariable("id") int id){
        Comment comment = new Comment();
        comment.setId(id);
        commentService.delete(comment);
        return true;
    }

    @GetMapping("/get/{foodId}")
    public List<Comment> getAll(@PathVariable("foodId") int id){
        Food food = new Food();
        food.setId(id);
        return  commentService.getByfood(food);
    }


}
