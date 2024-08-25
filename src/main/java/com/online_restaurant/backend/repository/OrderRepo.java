package com.online_restaurant.backend.repository;

import com.online_restaurant.backend.model.*;
import com.online_restaurant.backend.model.Enum.FoodType;
import com.online_restaurant.backend.util.DateFormating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Repository
public class OrderRepo implements BaseRepo<Order>{


    @Autowired
    private Connection connection;
    @Autowired
    private DateFormating dateFormating;

    @Override
    public List<Order> getAll(int limit) {

        final String q1 = "start transaction";
        final String q2 = "select ord.id id ,ord.userId userId , fd.name food,fd.price price , ur.username username ,ord.delivererId delivererId,fo.num num from orders ord inner join food_order fo" +
                " on ord.id = fo.orderId " +
                "inner join foods fd " +
                "on fd.id = fo.foodId " +
                "inner join users ur " +
                "on ur.id=ord.userId ";
        final String q3 = "select py.id id , py.amount amount,py.datePay datePay ,py.status status, ord.id orderId " +
                " from orders ord inner join " +
                "payments py on py.orderId= ord.id";
        final String q4 = "commit" ;
        List<Order> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();

            statement.execute(q1);
            ResultSet rs = statement.executeQuery(q2);


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
               else {
                   order = findList.get(0);
               }

                Food food = new Food();
                food.setName(rs.getString("food"));
                food.setPrice(rs.getDouble("price"));
                food.setNumber(rs.getInt("num"));
                order.getFoodList().add(food);
            }

            ResultSet rs2 = statement.executeQuery(q3);
            while (rs2.next()){
                Payment payment = new Payment();
                payment.setId(rs2.getInt("id"));
                payment.setStatus(rs2.getBoolean("status"));
                payment.setAmount(rs2.getDouble("amount"));
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
            statement.execute(q4);
            statement.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    public List<Order> getByUser(User userIN){

        final String q1 = "start transaction";
        final String q2 = "select ord.id id ,ord.userId userId , fd.name food,fd.price price , ur.username username ,ord.delivererId delivererId , fo.num num from orders ord inner join food_order fo" +
                " on ord.id = fo.orderId " +
                "inner join foods fd " +
                "on fd.id = fo.foodId " +
                "inner join users ur " +
                "on ur.id=ord.userId " +
                "where ord.userId="+userIN.getId();
        final String q3 = "select py.id id , py.amount amount,py.datePay datePay ,py.status status, ord.id orderId " +
                " from orders ord inner join " +
                "payments py on py.orderId= ord.id " +
                "where ord.userId="+userIN.getId();
        final String q4 = "commit" ;
        List<Order> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();

            statement.execute(q1);
            ResultSet rs = statement.executeQuery(q2);


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
                else {
                    order = findList.get(0);
                }
                Food food = new Food();
                food.setName(rs.getString("food"));
                food.setPrice(rs.getDouble("price"));
                food.setNumber(rs.getInt("num"));
                order.getFoodList().add(food);
            }

            ResultSet rs2 = statement.executeQuery(q3);
            while (rs2.next()){
                Payment payment = new Payment();
                payment.setId(rs2.getInt("id"));
                payment.setStatus(rs2.getBoolean("status"));
                payment.setAmount(rs2.getDouble("amount"));
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
            statement.execute(q4);
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
        final String q2 =String.format("select ord.id id, usr.id userId , usr.username username, fo.foodId foodId, fd.name food,fd.price price, fo.num num,addr.addr address,fd.typ type " +
                " from orders ord inner join food_order fo " +
                "on fo.orderId = ord.id " +
                "inner join foods fd " +
                "on fd.id= fo.foodId " +
                "inner join users usr " +
                "on usr.id = ord.userId " +
                "inner join addresses addr " +
                "on addr.userId=usr.id " +
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
            User user = new User();
           order = new Order();
            List<Food> foods = new ArrayList<>();
            Food food ;
            while (rs.next()){
                if(rs.getRow() == 1){
                    order.setId(rs.getInt("id"));
                    user.setId(rs.getInt("userId"));
                    user.setUsername(rs.getString("username"));
                    Address address = new Address();
                    address.setAddress(rs.getString("address"));
                    order.setAddress(address);
                    order.setCustomer(user);
                }
                food = new Food();
                food.setId(rs.getInt("foodId"));
                food.setName(rs.getString("food"));
                food.setPrice(rs.getDouble("price"));
                food.setNumber(rs.getInt("num"));
                food.setType(FoodType.valueOf(rs.getString("type").toUpperCase()));
                foods.add(food);
            }


            order.setFoodList(foods);
            ResultSet rs2 = statement.executeQuery(q3);

            Payment payment= new Payment();
            if(rs2.next()){
                payment.setId(rs2.getInt("id"));
                payment.setStatus(rs2.getBoolean("status"));
                payment.setAmount(rs2.getDouble("amount"));
//                payment.setOrder(order);
                order.setPayment(payment);
            }
            statement.execute(q4);
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(order);
        return order;
    }

    @Override
    public Order save(Order ob) {

        Statement statement=null;
        final String q1 = "Start transaction";
        final String q2 = String.format("insert into orders (userId) values (%d)",ob.getCustomer().getId());
        final String q3 = "select MAX(id) id from orders";
//        final String q5 = "insert into payments (amount,datePay,status,orderId) values " +
//                "(%f,\"%s\",%b,%d)";
        final String q4 = "commit";


        try {
            statement =connection.createStatement();
            statement.execute(q1);
            int rowCount = statement.executeUpdate(q2);
            ResultSet rs = statement.executeQuery(q3);
            if (rs.next()){
                ob.setId(rs.getInt("id"));
            }
    //            statement.executeUpdate(String.format(q5,0.0,dateFormating.format("yyyy-MM-dd",new Date()),
    //                    false,ob.getId()));
            rs = statement.executeQuery("select * from payments where orderId= "+ob.getId());

            if (rs.next()){
                Payment payment = new Payment();
                payment.setId(rs.getInt("id"));
                payment.setAmount(rs.getDouble("amount"));
                payment.setStatus(rs.getBoolean("status"));
                ob.setPayment(payment);
            }
            statement.execute(q4);
            statement.close();
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }


        return ob;
    }

    @Override
    public boolean delete(Order ob) {
        final String q1 = "start transaction";
        final String q2 = "delete from orders where id= "+ob.getId();
        final String q4 = "delete from payments where orderId= "+ob.getId();
        final String q3 = "commit";

        try (java.sql.Statement statement  = connection.createStatement()) {
            statement.execute(q1);
            int rowCount = statement.executeUpdate(q4);
            if (rowCount!=0)
                rowCount = statement.executeUpdate(q2);
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


    public boolean addFood(Food food,Order order,int num){
        final String q1 = "start transaction";
        final String q2 = String.format("update  food_order set orderId=%d,foodId=%d,num=%d where foodId=%d and orderId=%d",order.getId(),food.getId(),num,food.getId(),order.getId());
        final String q3 = String.format("insert into food_order (foodId,orderId,num) values (%d,%d,%d)",
                food.getId(),order.getId(),num);
        final String q4 = "commit";

        try(Statement statement = connection.createStatement()){
            statement.execute(q1);
            int rowcount= statement.executeUpdate(q2);
//            System.out.println("row count: "+rowcount);
            if (rowcount==0)
                rowcount = statement.executeUpdate(q3);
            statement.executeUpdate(q4);
            statement.close();
            return rowcount==0 ? false:true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean removeItem(Order order, Food food){
        final String q1 = "start transaction";
        final String q2 = String.format("delete from food_order where foodId=%d and orderId=%d",food.getId(),order.getId());
        final String q3 = "commit";

        int rowCount;
        try(Statement statement = connection.createStatement()){
            statement.execute(q1);
             rowCount =statement.executeUpdate(q2);
            statement.execute(q3);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return rowCount !=0;
    }

    public boolean pay(Order order){
        final String q1 = "start transaction";
        final String q2 = "update payments set status=1 where orderId="+order.getId();
        final String q3 = "commit";

        try(Statement statement = connection.createStatement()){
            statement.execute(q1);
            int roucout = statement.executeUpdate(q2);
            statement.execute(q3);
            return roucout != 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
