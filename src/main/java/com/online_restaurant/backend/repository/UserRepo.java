package com.online_restaurant.backend.repository;

import com.online_restaurant.backend.model.Address;
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
    public List<User> getAll(int limit) {
        PreparedStatement prs;
        List<User> users = new ArrayList<>();
        try {
           prs = connection.prepareStatement(String.format("call getAllUsers(%d,%d)",30,(limit-1)*30));
           ResultSet rs = prs.executeQuery();

           while (rs.next()){
               User user = new User();
               user.setId(rs.getInt("id"));
               user.setUsername(rs.getString("username"));
//               user.setPassword();
               user.setBirthdate(new Date(rs.getDate("birthdate").getTime()));
               user.setEmail(rs.getString("email"));
               user.setSignupDate(new Date(rs.getDate("singup_date").getTime()));
               user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
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
            prs = connection.prepareStatement("call getUser(?)");
            prs.setInt(1,id);
            ResultSet rs = prs.executeQuery();
            if (rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername("username");
                user.setEmail(rs.getString("email"));
                user.setBirthdate(new Date(rs.getDate("birthdate").getTime()));
                user.setSignupDate(new Date(rs.getDate("singup_date").getTime()));
                user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
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

    public User get(User user){
        final String q1 = "start transaction";
        final String q2 = String.format("call getUserByUsername(\"%s\",\"%s\")",user.getUsername(),user.getPassword());
        final String q3 = "commit";

        try(Statement statement = connection.createStatement()){
            statement.execute(q1);
            ResultSet rs = statement.executeQuery(q2);


            if(rs.next()){
                user.setId(rs.getInt("id"));
                user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
                user.setEmail(rs.getString("email"));
                user.setBirthdate(rs.getDate("birthdate"));
                user.setSignupDate(rs.getDate("signup_date"));
                user.setAddresses(getAllAddresses(user));
                statement.execute(q3);
            }else{
                statement.execute(q3);
                return null;
            }

            statement.execute(q3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    @Override
    public User save(User ob) {
        final String format = "yyyy-MM_dd";
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        String q1 = "start transaction";
        StringBuffer q2 = new StringBuffer();
        q2.append("insert into users (username,password,email,birthdate,singup_date,role) ");
        q2.append(String.format("values (\"%s\",\"%s\",\"%s\",\"%s\",now(),\"%s\")",
                ob.getUsername(),
                ob.getPassword(),
                ob.getEmail(),
                simpleDateFormat.format(ob.getBirthdate()),
                ob.getRole().toString()));

        final String q3 = "select id from users where username=\"%s\" and password=\"%s\" ".formatted(ob.getUsername(),ob.getPassword());

        Statement statement ;
        try {
            statement = connection.createStatement();
            statement.execute(q1);
            System.out.println(q2.toString());
            statement.executeUpdate(q2.toString());
            ResultSet rs = statement.executeQuery(q3);

            if(rs.next()){
                ob.setId(rs.getInt("id"));
            }
            statement.execute("commit");
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return ob;
    }

    @Override
    public boolean delete(User ob) {

        final String q1 = "START TRANSACTION";
        final String query =  "delete from users where id =\"%s\" or username=\"%s\" ".formatted(ob.getId(),ob.getUsername());


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
        String q2 = "update users set username=\"%s\"," +
                "password=\"%s\"," +
                "birthdate=\"%s\"," +
                "email=\"%s\" " +
                "where id = %d";

        q2 =String.format(q2 , ob.getUsername(),ob.getPassword(),
                dateFormating.format("yyyy-MM-dd",ob.getBirthdate()),
                ob.getEmail(),ob.getId());


        Statement statement;
        try {
            statement = connection.createStatement();
            statement.execute(q1);
            System.out.println(q2);
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




    public Address getAllAddresses(User user){
        Address address = new Address();
        try {
            Statement statement = connection.createStatement();

            statement.execute("start transaction");
            ResultSet rs =statement.executeQuery("select * from addresses where userId= "+user.getId());

            if ((rs.next())){
//                address = new Address();
                address.setId(rs.getInt("id"));
                address.setPostalCode(rs.getString("postal_code"));
//                address.setUser(user);
                address.setAddress(rs.getString("addr"));
                statement.execute("commit");
                statement.close();
            }
            else {
                statement.execute("commit");
                statement.close();
                return null;
            }



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    return address;
    }



    public Address addAddress(Address address){

        final String q1 ="start transaction";
        final String q2 = String.format("insert into addresses (addr,userId,postal_code) values (\"%s\",%d,\"%s\")",
                address.getAddress(),
                address.getUser().getId(),
                address.getPostalCode());

        final String q3 = "select id from addresses where userId ="+address.getUser().getId();
        final String q4 = "commit";

        try(Statement statement =connection.createStatement()){
            statement.execute(q1);
            int rowcount = statement.executeUpdate(q2);
            ResultSet rs;
            if (rowcount!=0){

                rs = statement.executeQuery(q3);
                if (rs.next()){
                    System.out.println("id: "+rs.getInt("id"));
                    address.setId(rs.getInt("id"));
                }
                statement.execute(q4);
                statement.close();
            }

            else {
                statement.execute(q4);
                statement.close();

                return null;
            }

            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return address;
    }

    public boolean UpadteAddAddress(Address address){

        try {
            Statement statement = connection.createStatement();
            statement.execute("start transaction");
            int rowcount = statement.executeUpdate(String.format("update addresses set addr=\"%s\" where id=%d",address.getAddress(),address.getId()));
            statement.execute("commit");
            if (rowcount==0){
                return false;
            }
           statement.close();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return true;
    }


}
