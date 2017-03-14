
package com.hyperwallet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.hyperwallet.clientsdk.Hyperwallet;
import com.twilio.Twilio;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class UpbringApplication {

    public static void main(String[] args) {
        SpringApplication.run(UpbringApplication.class, args);
    }

    @Value("${hyperwallet.user}")
    String hyperwalletUsername;

    @Value("${hyperwallet.password}")
    String hyperwalletPassword;

    @Value("${hyperwallet.program}")
    String hyperwalletProgram;
    
    @Value("${twilio.username}")
    String twilioUsername;
    
    @Value("${twilio.password}")
    String twilioPassword;

    @PostConstruct
    public void twilioClient() {
        Twilio.init(twilioUsername, twilioPassword);
    }

    @Bean
    public Hyperwallet walletClient() {
        return new Hyperwallet(hyperwalletUsername, hyperwalletPassword, hyperwalletProgram);
    }

}
