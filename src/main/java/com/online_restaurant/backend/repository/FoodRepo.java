package com.online_restaurant.backend.repository;

import com.online_restaurant.backend.ioUtil.ImageIo;
import com.online_restaurant.backend.model.Enum.FoodType;
import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.model.User;
import com.online_restaurant.backend.util.DateFormating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class FoodRepo implements  BaseRepo<Food>{

    @Autowired
    private Connection connection;
    @Autowired
    private DateFormating formating;

    @Autowired
    private ImageIo imageIo;

    @Value(("${root-path}"))
    private String root;


    @Override
    public List<Food> getAll( int limit) {

        Statement statement ;

        final String q1 = "start transaction";
        final  String q2 = String.format("select * from foods limit 30 offset %d",30*(limit-1));
        final String q3 = "select img.id id,  path,foodId from foods f" +
                " inner join food_img img " +
                "on f.id=img.foodId";
        final String q4 = "commit";
        List<Food> result = new ArrayList<>();


        try {
            statement = connection.createStatement();
            statement.execute(q1);
            ResultSet rs = statement.executeQuery(q2);


            while (rs.next()){
                Food food = new Food();
                food.setId(rs.getInt("id"));
                food.setName(rs.getString("name"));
                food.setPrice(rs.getDouble("price"));
                food.setStatus(rs.getBoolean("status"));
                food.setDescription(rs.getString("description"));
                food.setType(FoodType.valueOf(rs.getString("typ").toUpperCase()));
                result.add(food);

            }
            ResultSet imgset = statement.executeQuery(q3);

            while (imgset.next()){

                for (Food item :result){
                    if (imgset.getInt("foodId") == item.getId()){
                        if (item.getImagePath() == null){
                            item.setImagePath(new ArrayList<>());
                        }

                        item.getImagePath().add(imgset.getString("path"));
                        break;
                    }

                }

            }
            statement.execute(q4);
            statement.close();




        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return result;
    }

    @Override
    public Food get(int id) {

        Statement statement ;
        final String q1 = "start transaction";
        final String q2 = String.format("select * from foods where id=%d",id);
        final String q3 = String.format("select * from food_img where foodId = %d",id);
        final String q4 = "commit";
        Food food=null;

        try {
            statement = connection.createStatement();
            statement.execute(q1);
            ResultSet rs = statement.executeQuery(q2);




            if (rs.next()){
                food = new Food();
                food.setId(rs.getInt("id"));
                food.setName(rs.getString("name"));
                food.setPrice(rs.getDouble("price"));
                food.setStatus(rs.getBoolean("status"));
                food.setDescription(rs.getString("description"));
                food.setType(FoodType.valueOf(rs.getString("typ").toUpperCase()));

                ResultSet imgs = statement.executeQuery(q3);
                if (imgs.next()) {
                    food.setImagePath(new ArrayList<>());
                    food.getImagePath().add(imgs.getString("path"));

                    while(imgs.next()){
                        if (food.getImagePath() == null)
                            food.setImagePath(new ArrayList<>());

                    }
                }
            }
            statement.execute(q4);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return food;
    }

    @Override
    public Food save(Food ob) {
        Statement statement ;
        final String q1 = "start transaction";
        final String q2 = String.format("insert into foods (name,price,description,status,typ) " +
                "values (\"%s\",%f,\"%s\",%b,\"%s\") ",ob.getName(),ob.getPrice(),ob.getDescription(),ob.getStatus(),
                ob.getType().toString());
        final String q3 = String.format("select id from foods where name=\"%s\"",ob.getName());
        final String q4 = "commit";
        try {
            statement = connection.createStatement();
            statement.execute(q1);
            int rowCount = statement.executeUpdate(q2);
            if (rowCount != 0){
                ResultSet rs = statement.executeQuery(q3);
                rs.next();
                ob.setId(rs.getInt("id"));
            }
            statement.execute(q4);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ob;
    }

    @Override
    public boolean delete(Food ob) {

        Statement statement ;
        final String q1 = "start transaction";
        final String q2 = String.format("delete from foods where id=%d",ob.getId());
        final String q3 = "commit";

        try {
            statement = connection.createStatement();
            statement.execute(q1);
            int rowCount = statement.executeUpdate(q2);
            statement.execute(q3);
            statement.close();
            if (rowCount == 0){
                return false;
            }
            else {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public boolean update(Food ob) {
        final String q1 = "start transaction";
        final String q2 = String.format("update foods set name=\"%s\",price=%f,description=\"%s\",status=%b,typ=\"%s\" " +
                        "where id=%d",
                ob.getName(),ob.getPrice(),ob.getDescription(),ob.getStatus(),ob.getType().toString(),ob.getId());
        final String q3 = "commit";
        Statement statement ;
        try {
            statement = connection.createStatement();
            statement.execute(q1);
            int rowCount  = statement.executeUpdate(q2);
            statement.execute(q3);
            statement.close();

            if (rowCount==0){
                return false;
            }
            else
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }


    public boolean saveImg(Food food,byte[] bytes,String suffix) throws IOException {
        UUID uuid = UUID.randomUUID();
        String filename = uuid.toString()+food.getId();
        String fullPath = root+"/images/food/"+filename+"."+suffix;
        try {
            Statement statement = connection.createStatement();
            statement.execute("start transaction");
            statement.executeUpdate(String.format("insert into food_img (path,foodId) values (\"%s\",%d)",
                    fullPath,food.getId()));

            statement.execute("commit");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

       return imageIo.saveImage(filename+"."+suffix,root+"/images/food",bytes);

    }


    public byte[][] getImages(Food food){

        final String q1 = "start transaction";
        final String q2 = String.format("select path from food_img where foodId=%d",food.getId());
        final String q3 = "commit";
        List<byte[]>  bytes;
        try {
            Statement statement = connection.createStatement();
            statement.execute(q1);
            ResultSet rs = statement.executeQuery(q2);

            bytes = new ArrayList<>();
            while (rs.next()){
                bytes.add(imageIo.getImage(rs.getString("path")));
            }
            statement.execute(q3);
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytes.toArray(byte[][]::new);
    }




}
