package com.ncgroup.marketplaceserver.Controllers;




import com.ncgroup.marketplaceserver.Services.Email.EmailSenderValidation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RegistrationController {

    //Change to get token from database
    private String setActivationToken = EmailSenderValidation.setActivationToken;

    //When user go by link copy token and compare with saved token
    @GetMapping(path= "/confirm-account")
    public String confirmUser(@RequestParam("token") String token) {

    //Token comparison
        if (token.equals(setActivationToken))
            return "Congratulations, you have confirmed your mail!";
        else return "Something went wrong and registration failed";
    }
}

