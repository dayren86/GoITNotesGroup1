package com.groupone;

import com.groupone.users.Users;
import com.groupone.users.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class MainController {
    UsersService usersService;

    @GetMapping("/")
    public String showHomePage(){
        return "redirect:/note/list";
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new Users());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView processRegister(@RequestParam(name = "setEmail") String email,
                                        @RequestParam(name = "setPassword") String password) {
        if (email.length() < 5 || email.length() > 20) {
            return showRegistrationForm().addObject("error", 0);
        } else {
            usersService.createUser(email, password);
            return login();
        }
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

}
