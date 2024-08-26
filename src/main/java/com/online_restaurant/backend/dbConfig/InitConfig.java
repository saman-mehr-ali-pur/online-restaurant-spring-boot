package com.online_restaurant.backend.dbConfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class InitConfig implements CommandLineRunner {

    @Value("${root-path}")
    private String root;
    @Override
    public void run(String... args) throws Exception {
        Path path = Paths.get(root+"/restaurant");
        if (!Files.exists(path)){
            System.out.println("create directory");
            Files.createDirectory(path);
        }
    }
}
