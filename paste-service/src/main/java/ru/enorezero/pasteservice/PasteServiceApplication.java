package ru.enorezero.pasteservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PasteServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PasteServiceApplication.class, args);
	}

}
