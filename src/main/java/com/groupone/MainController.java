package com.groupone;

import com.groupone.users.Users;
import com.groupone.users.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@AllArgsConstructor
public class MainController {
    UsersService usersService;

    @GetMapping("/")
    public String showHomePage() {
        return "redirect:/note/list";
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("user", new Users());
        return modelAndView;
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam(name = "setEmail") String email,
                                  @RequestParam(name = "setPassword") String password,
                                  Model model) {
        if (email.length() < 5 || email.length() > 20) {
            model.addAttribute("error", 0);
            return "register";
        }
        if (usersService.findByEmail(email) != null) {
            model.addAttribute("errorEmail", 0);
            return "register";
        }

        usersService.createUser(email, password);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView("login");
        return modelAndView;
    }

}
