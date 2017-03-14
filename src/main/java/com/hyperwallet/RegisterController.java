/**
 * 
 */
package com.hyperwallet;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyperwallet.clientsdk.Hyperwallet;
import com.hyperwallet.clientsdk.model.HyperwalletUser;

import javax.annotation.Resource;

/**
 * 
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
@Controller
@RequestMapping("")
public class RegisterController {

    @Resource
    protected Hyperwallet client;
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody HyperwalletUser index() {
        HyperwalletUser user = new HyperwalletUser();
        user
          .clientUserId("test-client-id-2")
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
    
}
