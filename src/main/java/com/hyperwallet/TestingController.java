/**
 * 
 */
package com.hyperwallet;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyperwallet.clientsdk.Hyperwallet;
import com.hyperwallet.clientsdk.model.HyperwalletUser;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import javax.annotation.Resource;

/**
 * 
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
@Controller
public class TestingController {

    @Resource
    protected Hyperwallet client;
    
    @GetMapping(value = "/wallet", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody HyperwalletUser wallet() {
        HyperwalletUser user = new HyperwalletUser();
        user
        .clientUserId("test")
        .profileType(HyperwalletUser.ProfileType.INDIVIDUAL)
        .firstName("Daffyd")
        .lastName("y Goliath")
        .email("testmail-1@hyperwallet.com")
        .addressLine1("123 Main Street")
        .city("Austin")
        .stateProvince("TX")
        .country("US")
        .postalCode("78701");
        
        HyperwalletUser createdUser = client.createUser(user);
        return createdUser;
    }
    
    @GetMapping(value = "/twilio", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String twilio() {
        Message message = Message.creator(
            new PhoneNumber("+19723455790"), new PhoneNumber("+15672035178"),
            "Oh my god it works")
            .create();
        
        return message.getSid();
    }
}
