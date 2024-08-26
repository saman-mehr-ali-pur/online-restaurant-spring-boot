package com.online_restaurant.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class BackendApplication  {


	public static void main(String[] args) {
//		System.out.println("root: ");
		SpringApplication.run(BackendApplication.class, args);
	}


}
