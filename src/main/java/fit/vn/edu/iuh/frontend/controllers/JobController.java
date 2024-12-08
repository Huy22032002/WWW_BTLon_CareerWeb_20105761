package fit.vn.edu.iuh.frontend.controllers;

import org.springframework.ui.Model;
import fit.vn.edu.iuh.backend.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/job")
public class JobController {
    @Autowired
    private JobService jobService;
    @GetMapping("/listJob")
    public String listJob(Model model) {
        model.addAttribute("lstJob", jobService.findAll());
        return "job_screen/listJob";
    }
}
