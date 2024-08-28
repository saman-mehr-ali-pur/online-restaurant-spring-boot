package com.online_restaurant.backend;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
@Slf4j
public class BackendApplication  {

//	public static Logger logger=  LogManager.getLogger();
	public static void main(String[] args) {
		log.info("starting app");
//		System.out.println("root: ");
		SpringApplication.run(BackendApplication.class, args);
	}


}
