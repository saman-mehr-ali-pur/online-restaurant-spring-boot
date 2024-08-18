package com.online_restaurant.backend.repository;

import com.online_restaurant.backend.model.Food;
import com.online_restaurant.backend.model.Order;
import com.online_restaurant.backend.model.Payment;
import com.online_restaurant.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Repository
public class OrderRepo implements BaseRepo<Order>{


    @Autowired
    private Connection connection;

    @Override
    public List<Order> getAll(int limit) {

        final String q1 = "start transaction";
        final String q2 = "select ord.id id ,ord.userId userId , fd.name food,fd.price price , ur.username username ,ord.delivererId delivererId  from orders ord inner join food_order fo" +
                " on ord.id = fo.orderId " +
                "inner join foods fd " +
                "on fd.id = fo.foodId " +
                "inner join users ur " +
                "on ur.id=ord.userId ";
        final String q3 = "select py.id id , py.amount amount,py.datePay datePay ,py.status status, ord.id orderId, " +
                " from orders ord inner join " +
                "payments py on py.orderId= ord.id";
        final String q4 = "commit" ;
        List<Order> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();

            statement.execute(q1);
            ResultSet rs = statement.executeQuery(q2);
            ResultSet rs2 = statement.executeQuery(q3);
            statement.execute(q4);
            Order order;
            while (rs.next()){
                order = new Order();
                order.setId(rs.getInt("id"));
                Order finalOrder = order;
                List<Order> findList = result.stream().filter(item -> {return item.getId() == finalOrder.getId() ;}).toList();
               if(findList.isEmpty()){
                   order.setFoodList(new ArrayList<>());
                   result.add(order);

                   User user = new User();
                   user.setUsername(rs.getString("username"));
                   user.setId(rs.getInt("userId"));
                   order.setCustomer(user);

                   User deliverer = new User();
                   deliverer.setId(rs.getInt("delivererId"));
                   order.setDeliverer(deliverer);

               }
               order  = findList.get(0);

                Food food = new Food();
                food.setName(rs.getString("food"));
                food.setPrice(rs.getDouble("price"));
                order.getFoodList().add(food);
            }


            while (rs2.next()){
                Payment payment = new Payment();
                payment.setId(rs2.getInt("id"));
                payment.setStatus(rs.getBoolean("status"));
                payment.setAmount(rs.getDouble("amount"));
                Optional<Order> ord =  result.stream().
                        filter(item -> {
                            try {
                                return item.getId()==rs2.getInt("orderId");
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }).
                        findFirst();

                if (!ord.isEmpty()){
                    ord.get().setPayment(payment);
                }


            }

            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public Order get(int id) {
        Statement statement ;
        final String q1 = "start transaction";
        final String q2 =String.format("select ord.id id, usr.id userId , usr.username username, food_order.foodId foodId, fd.name food,fd.price price" +
                " from orders ord inner join food_order " +
                "on food_order.orderId = ord.id " +
                "inner join foods fd " +
                "on fd.id= food_order.foodId " +
                "inner join users usr " +
                "on usr.id = ord.userId " +
                "where ord.id = %d ",id);

        final String q3 = "select py.id id, py.amount amount, py.datePay datePay ,py.status status, py.orderId orderId " +
                "from orders ord inner join payments py " +
                "on py.orderId = ord.id " +
                "where ord.id= "+id;
        final String q4 = "commit";
        Order order;
        try {
            statement = connection.createStatement();

            statement.execute(q1);
            ResultSet rs = statement.executeQuery(q2);
            ResultSet rs2 = statement.executeQuery(q3);
            statement.execute(q4);
            User user = new User();
           order = new Order();
            List<Food> foods = new ArrayList<>();
            Food food ;
            while (rs.next()){
                if(rs.getRow() == 1){
                    order.setId(rs.getInt("id"));
                    user.setId(rs.getInt("userId"));
                    user.setUsername("username");
                    order.setCustomer(user);
                }
                food = new Food();
                food.setId(rs.getInt("foodId"));
                food.setName(rs.getString("food"));
                food.setPrice(rs.getDouble("price"));
                foods.add(food);
            }


            order.setFoodList(foods);

            Payment payment= new Payment();
            if(rs2.next()){
                payment.setId(rs2.getInt("id"));
                payment.setStatus(rs.getBoolean("status"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setOrder(order);
                order.setPayment(payment);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return order;
    }

    @Override
    public Order save(Order ob) {

        Statement statement;
        final String q1 = "Start transaction";
        final String q2 = String.format("insert into orders (userId) values (%d)",ob.getCustomer().getId());
        final String q3 = String.format("select MAX(id) id from orders");
        final String q4 = "commit";

        try {
            statement =connection.createStatement();
            statement.execute(q1);
            int rowCount = statement.executeUpdate(q2);
            ResultSet rs = statement.executeQuery(q3);
            statement.execute(q4);
            if (rs.next()){
                ob.setId(rs.getInt("id"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return ob;
    }

    @Override
    public boolean delete(Order ob) {
        final String q1 = "start transaction";
        final String q2 = "delete from orders where id= "+ob.getId();
        final String q3 = "commit";

        try (java.sql.Statement statement  = connection.createStatement()) {
            statement.execute(q1);
            int rowCount = statement.executeUpdate(q2);
            statement.execute(q3);
            if (rowCount!=0)
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return false;
    }

    @Override
    public boolean update(Order ob) {
        final String q1 = "start transaction";
        final String q2 = String.format("update orders set delivererId=%d, set addressId=%d",
                ob.getDeliverer().getId(),ob.getAddress().getId());
        final String q3 = "commit";

        try(Statement statement = connection.createStatement()){

            statement.execute(q1);
            int rowCount = statement.executeUpdate(q2);
            statement.execute(q3);

            if(rowCount!=0)
                return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return false;
    }


    public boolean addFood(Food food,Order order){
        final String q1 = "start transaction";
        final String q2 = String.format("insert into food_order (foodId,orderId) values (%d,%d)",
                food.getId(),order.getId());
        final String q3 = "commit";

        try(Statement statement = connection.createStatement()){
            statement.execute(q1);
            statement.executeUpdate(q2);
            statement.execute(q3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  true;
    }

}
