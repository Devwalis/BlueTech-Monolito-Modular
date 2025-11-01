package com.ecommerce.bluetech;

import org.springframework.boot.SpringApplication;

public class TestEcommerceBluetechApplication {

	public static void main(String[] args) {
		SpringApplication.from(EcommerceBluetechApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
