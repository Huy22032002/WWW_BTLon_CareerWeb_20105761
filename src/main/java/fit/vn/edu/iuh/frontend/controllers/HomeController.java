package fit.vn.edu.iuh.frontend.controllers;

import fit.vn.edu.iuh.backend.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
@Controller
public class HomeController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("lstCompany", companyService.findAll());
        return "home";
    }

    @GetMapping("/candidate_login")
    public String loginCandidate(){
        return "/candidate_screen/login";
    }
    @GetMapping("/employer_login")
    public String loginEmployer(){
        return "/company_screen/login";
    }
}
