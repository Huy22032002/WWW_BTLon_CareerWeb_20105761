package vn.edu.iuh.fit.controllers;

import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.iuh.fit.models.Job;
import vn.edu.iuh.fit.services.JobApplicationService;
import vn.edu.iuh.fit.services.JobService;
import vn.edu.iuh.fit.services.JobSkillService;
import vn.edu.iuh.fit.services.SkillService;

import java.security.Principal;

@Controller
@RequestMapping("/jobs")
public class JobController {
    @Autowired
    private JobService jobService;

    @Autowired
    private SkillService skillService;
    @Autowired
    private JobSkillService jobSkillService;
    @Autowired
    private JobApplicationService jobApplicationService;

    // Hiển thị danh sách việc làm
    @GetMapping
    public String listJobs(Model model) {
        model.addAttribute("jobs", jobService.findAllJobs());
        return "jobs/jobs.html";
    }

    // Trang thêm mới công việc
    @GetMapping("/new")
    public String showNewJobForm(Model model) {
        model.addAttribute("job", new Job());
        model.addAttribute("skills", skillService.findAll());
        return "jobs/new";
    }

    // Xử lý việc thêm mới công việc
    @PostMapping
    public String addJob(@ModelAttribute Job job) {
        jobService.saveJob(job);
        return "redirect:/jobs";
    }

    @PostMapping("/jobApply/{jobId}/apply")
    public String applyForJob(
            @PathVariable Long jobId,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        if (principal == null) {
            return "redirect:/login"; // Chuyển hướng nếu chưa đăng nhập
        }

        try {
            String username = principal.getName(); // Lấy tên đăng nhập của người dùng
            jobApplicationService.applyForJob(jobId, username); // Gọi service để xử lý logic ứng tuyển
            redirectAttributes.addFlashAttribute("successMessage", "Bạn đã ứng tuyển thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
        }

        return "redirect:/candidates/applied-jobs";
    }

}