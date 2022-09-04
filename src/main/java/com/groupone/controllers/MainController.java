package com.groupone.controllers;

import com.groupone.users.Users;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class MainController {
//    private UserRepository userRepo;

    @GetMapping("/")
    public String showHomePage(){
        return "redirect:/note/list";
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView("signup_form");
        modelAndView.addObject("user", new Users());
        return modelAndView;
    }

//    @PostMapping("/process_register")
//    public void processRegister(Users user, HttpServletResponse response) throws IOException {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
//
//        userRepo.save(user);
//
//        response.sendRedirect("/login");
//    }

}
