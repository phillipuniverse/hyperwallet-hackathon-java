/**
 * 
 */
package com.hyperwallet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hyperwallet.clientsdk.Hyperwallet;
import com.hyperwallet.clientsdk.model.HyperwalletPayment;
import com.hyperwallet.clientsdk.model.HyperwalletUser;
import com.hyperwallet.dao.GrantRequestRepository;
import com.hyperwallet.domain.GrantRequest;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.UUID;

import javax.annotation.Resource;

/**
 * 
 * 
 * @author Phillip Verheyden (phillipuniverse)
 */
@Controller
public class RegisterController {
    
    private static final Log LOG = LogFactory.getLog(RegisterController.class);

    @Resource
    protected Hyperwallet client;
    
    @Resource
    protected GrantRequestRepository requestRepo;
    
    @GetMapping("/")
    public String register() {
        return "register";
    }
    
    @PostMapping("register")
    public String submitRegister(@ModelAttribute GrantRequest request) {
        request = requestRepo.save(request);
        sendMessage(request.phone, "Your request has been submitted, we are accepting donations at http://4a455c0c.ngrok.io/donate/" + request.id);
        createUser(request);
        return "register";
    }
    
    @GetMapping("/donate/{userId}")
    public String viewDonate(@PathVariable Long userId, Model model) {
        GrantRequest request = requestRepo.findOne(userId);
        model.addAttribute("request", request);
        return "donate";
    }
    
    @PostMapping("/donate/{userId}")
    public @ResponseBody String donate(@RequestParam String amount, @RequestParam Long requestId) {
        GrantRequest request = requestRepo.findOne(requestId);
        sendMoney(15, request);
        sendMessage(request.phone, "Someone has donated " + amount + " on your behalf!");
        return "Success!";
    }
    
    @GetMapping(value = "user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody GrantRequest viewRequest(@PathVariable Long userId) {
        return requestRepo.findOne(userId);
    }
    
    private void sendMoney(double amount, GrantRequest request) {
        try {
            HyperwalletPayment payment = new HyperwalletPayment()
                .amount(amount)
                .purpose("OTHER")
                .currency("USD")
                .clientPaymentId(UUID.randomUUID().toString());
            payment.setDestinationToken(request.getToken());
            client.createPayment(payment);
        } catch (Exception e) {
            LOG.error(e);
        }
    }
    
    private HyperwalletUser createUser(GrantRequest request) {
        try {
            HyperwalletUser user = new HyperwalletUser();
            user
              .clientUserId(request.id.toString() + UUID.randomUUID().toString().substring(12))
              .profileType(HyperwalletUser.ProfileType.INDIVIDUAL)
              .firstName(request.firstName)
              .lastName(request.lastName)
              .email(request.email)
              .addressLine1(request.address)
              .city(request.city)
              .stateProvince(request.state)
              .country("US")
              .postalCode(request.zip);
    
            HyperwalletUser createdUser = client.createUser(user);
            String token = createdUser.getToken();
            request.setToken(token);
            requestRepo.save(request);
            return createdUser;
        } catch (Exception e) {
            LOG.error(e);
            return null;
        }
    }
    
    public String sendMessage(String phone, String message) {
        String phoneNumber = phone;
        if (StringUtils.isEmpty(phone)) {
            phoneNumber = "+19723455790";
        }
        if (!phoneNumber.startsWith("+1")) {
            phoneNumber = "+1" + phoneNumber;
        }
        
        Message msg = Message.creator(
            new PhoneNumber(phoneNumber), new PhoneNumber("+15672035178"),
            message)
            .create();

        return msg.getSid();
    }
    
}
