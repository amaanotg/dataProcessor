package com.dataLoader.app;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SpringrestApplication {
	public static void main(String[] args) throws BeansException, Exception {
		SpringApplication.run(SpringrestApplication.class, args);
	}
}
