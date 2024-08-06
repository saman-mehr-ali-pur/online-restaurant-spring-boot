package com.online_restaurant.backend.repository;

import com.online_restaurant.backend.model.Enum.Role;
import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.util.DateFormating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;

@Repository
public class UserRepo implements BaseRepo<User>{

    @Autowired
    private Connection connection;
    @Autowired
    private DateFormating dateFormating;

    @Override
    public List<User> getAll(User ob) {
        PreparedStatement prs;
        List<User> users = new ArrayList<>();
        try {
           prs = connection.prepareStatement("call find_all");
           ResultSet rs = prs.executeQuery();

           while (rs.next()){
               User user = new User();
               user.setId(rs.getInt("id"));
               user.setUsername(rs.getString("username"));
//               user.setPassword();
               user.setBirthdate(new Date(rs.getDate("birthdate").getTime()));
               user.setEmail(rs.getString("email"));
               user.setSignupDate(new Date(rs.getDate("singup_date").getTime()));
               user.setRole(Role.valueOf(rs.getString("role")));
               users.add(user);
           }
           prs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return users;
    }

    @Override
    public User get(int id) {
        PreparedStatement prs ;
        User user ;
        try {
            prs = connection.prepareStatement("call get(?)");
            prs.setInt(1,id);
            ResultSet rs = prs.executeQuery();
            if (rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername("username");
                user.setEmail(rs.getString("email"));
                user.setBirthdate(new Date(rs.getDate("birthdate").getTime()));
                user.setSignupDate(new Date(rs.getDate("singup_date").getTime()));
                return user;
            }
            else {
                prs.close();
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    @Override
    public User save(User ob) {
        final String format = "yyyy-MM_dd";
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String q1 = "start transaction";
        StringBuffer q2 = new StringBuffer();
        q2.append("insert into users (username,password,email,birthdate,signup_date,role) ");
        q2.append(String.format("values(%s,%s,%s,%s,now(),%s)",
                ob.getUsername(),
                ob.getPassword(),
                ob.getEmail(),
                simpleDateFormat.format(ob.getBirthdate()),
                ob.getRole().toString()));

        final String q3 = "select id from users weher username=%s and password=%s".formatted(ob.getUsername(),ob.getPassword());

        Statement statement ;
        try {
            statement = connection.createStatement();
            statement.execute(q1);
            statement.executeUpdate(q2.toString());
            ResultSet rs = statement.executeQuery(q3);
            statement.execute("commit");
            if(rs.next()){
                ob.setId(rs.getInt("id"));
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return ob;
    }

    @Override
    public boolean delete(User ob) {

        final String q1 = "START TRANSACTION";
        final String query =  "delete from users where id =%s or username=%s".formatted(ob.getId(),ob.getUsername());


      Statement statement;

        try {
            statement = connection.createStatement();
            statement.execute(q1);
           int rs = statement.executeUpdate(query);
            statement.execute("commit");

            if (rs==0){
                statement.close();
                return false;

            }
                else{
                    statement.close();
                    return true;
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(User ob) {
        final String q1 = "START TRANSACTION";
        String q2 = "update users set username=%s," +
                "password=%s," +
                "birthdate=%s," +
                "email=%s";

        q2 = q2.formatted(ob.getUsername(),ob.getPassword(),
                dateFormating.format("yyyy-MM-dd",ob.getBirthdate()),
                ob.getEmail());


        Statement statement;
        try {
            statement = connection.createStatement();
            statement.execute(q1);
            int rs = statement.executeUpdate(q2);
            statement.execute("commit");
            if (rs==0){
                statement.close();
                return false;
            }
            else {
                statement.close();
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




}
