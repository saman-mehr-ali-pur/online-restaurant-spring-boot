package com.online_restaurant.backend.dbConfig;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;


@Configuration
public class Confige {
    private final String url = "jdbc:mysql://localhost:3306/restaurant";
    private final String username = "root";
    private final String password = "Saman";
//    private Logger logger = LogManager.getLogger();
//    @Bean
//    public Connection getConnection(){
//        Connection connection;
//        System.out.println("database connection");
//
////        logger.trace("database connection established");
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(url , username,password);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//
//        return  connection;
//    }


    @Bean
    public EntityManagerFactory createEntityMangerFactory(){
        return Persistence.createEntityManagerFactory("jpa-unit");
    }

}
