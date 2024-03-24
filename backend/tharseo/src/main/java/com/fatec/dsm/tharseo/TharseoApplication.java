package com.fatec.dsm.tharseo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
public class TharseoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TharseoApplication.class, args);
	}

}
