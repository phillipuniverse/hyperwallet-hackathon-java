package com.hyperwallet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hyperwallet.clientsdk.Hyperwallet;

@SpringBootApplication
public class UpbringApplication {

	public static void main(String[] args) {
		SpringApplication.run(UpbringApplication.class, args);
	}
	
	@Value("${hyperwallet.user}")
	String username;
	
	@Value("${hyperwallet.password}")
	String password;
	
	@Value("${hyperwallet.program}")
	String program;
	
	@Bean
	public Hyperwallet client() {
	    return new Hyperwallet(username, password, program);
	}
	
}
