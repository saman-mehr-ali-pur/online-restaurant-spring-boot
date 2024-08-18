package com.online_restaurant.backend.controller.api;


import com.online_restaurant.backend.model.Comment;
import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/all/{foodId}")
    public List<Comment> getAll(@PathVariable("foodId") int id, @RequestParam("limit") int limit){
        Food food = new Food();
        food.setId(id);

        return commentService.getAll(food,limit);
    }


    @PostMapping("/save")
    public Comment saveComment(@RequestBody Comment comment){
        return commentService.save(comment);
    }


    @DeleteMapping("/delete/{id}")
    public boolean deleteComment(@PathVariable("id") int id){
        Comment comment = new Comment();
        comment.setId(id);
        return commentService.deleteComment(comment);
    }
}
