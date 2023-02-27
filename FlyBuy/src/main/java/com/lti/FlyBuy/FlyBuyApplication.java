package com.lti.FlyBuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.lti"})
@EntityScan(basePackages = "com.lti")
public class FlyBuyApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlyBuyApplication.class, args);
	}

}
