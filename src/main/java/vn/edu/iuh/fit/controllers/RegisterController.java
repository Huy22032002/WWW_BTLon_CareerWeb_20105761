package vn.edu.iuh.fit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.iuh.fit.models.Account;
import vn.edu.iuh.fit.models.Candidate;

@Controller
public class RegisterController {

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("candidate", new Candidate());
        return "singUp";
    }

}

