package com.online_restaurant.backend.dbConfig;


import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


@Configuration
public class Confige {
    private final String url = "jdbc:mysql://localhost:3306/restaurant";
    private final String username = "root";
    private final String password = "1111";
//    private Logger logger = LogManager.getLogger();
    @Bean
    public Connection getConnection(){
        Connection connection;
        System.out.println("database connection");

//        logger.trace("database connection established");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url , username,password);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return  connection;
    }

}
