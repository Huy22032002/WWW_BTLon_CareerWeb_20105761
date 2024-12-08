package fit.vn.edu.iuh.frontend.controllers;

import fit.vn.edu.iuh.backend.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @GetMapping("/listCompany")
    public String listCompany(Model model) {
        model.addAttribute("lstCompany", companyService.findAll());
        return "company_screen/listCompany";
    }

}
