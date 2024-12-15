package vn.edu.iuh.fit.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.iuh.fit.models.*;
import vn.edu.iuh.fit.repositories.AccountRepository;
import vn.edu.iuh.fit.services.*;

import java.security.Principal;
import java.util.*;

@Controller("home")
public class HomeController {
    @Autowired
    private JobService jobService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private JobSkillService jobSkillService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JobApplicationService jobApplicationService;

    @GetMapping({"/", "/home"})
    public String showIndexPage(Model model) {
        List<Company> companies = companyService.findAll();
        List<Job> jobs = jobService.findAll();

        model.addAttribute("companies", companies);
        model.addAttribute("jobs", jobs);

        return "index";
    }

    @GetMapping("/jobs/{jobId}")
    public String getJobDetail(@PathVariable("jobId") Long jobId, Model model) {
        Optional<Job> jobOptional = jobService.findJobById(jobId);

        if (jobOptional.isEmpty()) {
            return "redirect:/home";
        }

        Job job = jobOptional.get(); // Lấy giá trị Job thực tế từ Optional

        // Lấy thông tin công ty của công việc
        Company company = companyService.findCompanyById(job.getCompany().getId());
        List<JobSkill> jobSkills = jobSkillService.findSkillsByJobId(jobId);

        model.addAttribute("job", job); // Đưa Job vào model
        model.addAttribute("company", company);
        model.addAttribute("jobSkills", jobSkills);

        return "jobs/details";
    }

    @GetMapping("/home/check")
    public String home(Principal principal) {
        Account account = accountRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getCandidate() == null) {
            return "redirect:/candidates/register/full-info?accountId=" + account.getId();
        }

        return "index";
    }

    @PostMapping("/jobs/{jobId}/apply")
    public String applyForJob(@PathVariable Long jobId, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
        }

        try {
            String username = principal.getName();
            jobApplicationService.applyForJob(jobId, username);
            redirectAttributes.addFlashAttribute("successMessage", "Ứng tuyển thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ứng tuyển thất bại: " + e.getMessage());
        }

        return "redirect:/home" + jobId;
    }

}
