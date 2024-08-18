package com.online_restaurant.backend.repository;

import com.online_restaurant.backend.model.Comment;
import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.util.DateFormating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.tags.EscapeBodyTag;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CommentRepo  {
    @Autowired
    private Connection connection;

    @Autowired
    private DateFormating dateFormating;

    public List<Comment> getAllCommentByFood(Food food ,int limit){

        final String q1 = "start transaction";
        final String q2 = String.format("select c.id id, usf.foodID foodId, usf.userId userId, c.comment comment  from user_comment_food usf inner join" +
                " comments c on usf.commentId=c.id " +
                "where usf.foodId=%d limit 30 offset %d",food.getId(),(limit -1)*30);
        final String q3 = "commit";

        Statement statement ;

        List<Comment> comments = new ArrayList<>();
        try {
            statement = connection.createStatement();
            statement.execute(q1);
            ResultSet rs = statement.executeQuery(q2);
            statement.execute(q3);
            while (rs.next()){
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setComment(rs.getString("comment"));
                User user = new User();
                user.setId(rs.getInt("userId"));
                comment.setUser(user);
                Food food1 = new Food();
                food1.setId(rs.getInt("foodId"));
                comment.setFood(food1);
                comments.add(comment);

            }
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  comments;
    }



    public Comment saveComment(User user,Food food,Comment comment){

        final String q1  = "start transaction";
        final String q2 = String.format("insert into comments (comment,date) values (%s,now())",
                comment.getComment());

        final String q3 = "select MAX(id) id from comments";
//        final String q4 = String.format("insert into  user_comment_food (userId,foodId,commentId) values (%d,%d,%d)",
//                user.getId(),food.getId(),comment.getId());

        final String q5 = "commit";


        try {
            Statement statement = connection.createStatement();

            statement.execute(q1);
           int rowCount1 = statement.executeUpdate(q2);
            ResultSet rs =statement.executeQuery(q3);
            if (rs.next())
                comment.setId(rs.getInt("id"));
            int rowCount2 = statement.executeUpdate(String.format("insert into  user_comment_food (userId,foodId,commentId) values (%d,%d,%d)",
                    user.getId(),food.getId(),comment.getId()));
            statement.execute(q5);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return  comment;
    }


    public boolean deleteComment(Comment comment){

        final String q1 = "start transaction";
        final String q2 = "delete from comments where id ="+comment.getId();
        final String q3 = String.format("delete from user_comment_food where commentId=%d",comment.getId());
        final String q4 = "commit";

        try {
            Statement statement = connection.createStatement();
            statement.execute(q1);
            int rc1 = statement.executeUpdate(q2);
            int rc2 =statement.executeUpdate(q3);
            statement.execute(q4);
            if (rc2==0 && rc1==0)
                return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
