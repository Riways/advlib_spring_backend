package api.controller;


import api.entity.User;
import api.service.email_service.EmailService;
import api.service.user_service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "${frontend.localserver}")
@RequestMapping("api/user")
public class UserController {

    UserService userService;
    
    @Autowired
    EmailService emailService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/is-username-available/{username}")
    public boolean isUsernameAvailable(@PathVariable("username") String username){
        return userService.isUsernameAvailable(username);
    }
    
    @GetMapping("/is-email-available/{email}")
    public boolean isEmailAvailable(@PathVariable("email") String email){
        return userService.isEmailAvailable(email);
    }
    
    @PostMapping("save-user")
    public User saveUser(@ModelAttribute("user")  User user){
        userService.saveUserAsUser(user);
        return user;
    }
    
    @PostMapping("get-verification-code")
    public String getVerificationCodeOnEmail(HttpServletRequest req) throws Exception{
    	String email = req.getParameter("email");
    	if(email == null) {
    		throw new Exception("no mail was found in request");
    	}
    	String code = emailService.generateCode();
    	emailService.sendEmail(code, email);  
        return code;
    }


}
