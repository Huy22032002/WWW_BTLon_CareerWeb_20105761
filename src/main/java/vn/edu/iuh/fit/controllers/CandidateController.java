package vn.edu.iuh.fit.controllers;

import vn.edu.iuh.fit.repositories.*;
import vn.edu.iuh.fit.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.iuh.fit.models.*;

import java.security.Principal;
import java.util.*;

@SessionAttributes("accountId")
@Controller
@RequestMapping("/candidates")
public class CandidateController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private CandidateService candidateServices;
    @Autowired
    private JobApplicationService jobApplicationService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private JobService jobService;
    @Autowired
    private CandidateSkillService candidateSkillService;
    @Autowired
    private JobSkillService jobSkillService;
    @Autowired
    private SkillService skillService;

    @GetMapping("/suggestJob")
    public String suggestJobs(Model model, Principal principal) {
        // Lấy thông tin ứng viên đã đăng nhập
        Account account = accountService.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Candidate candidate = account.getCandidate();
        System.out.println(candidate.getFullName());
        if (candidate == null) {
            return "redirect:/login";
        }

        // Lấy danh sách kỹ năng của ứng viên
        List<Skill> candidateSkills = candidateSkillService.findSkillsByCandidateId(candidate.getId());
        for (Skill skill : candidateSkills) {
            System.out.println(skill.getSkillName());
        }

        // Tìm công việc dựa trên kỹ năng của ứng viên
        List<Job> suggestedJobs = new ArrayList<>();
        for (Skill skill : candidateSkills) {
            List<Job> jobs = jobSkillService.findJobBySkillId(skill.getId());
            for (Job job : jobs) {
                if (!suggestedJobs.contains(job)) {
                    suggestedJobs.add(job);
                }
            }
        }
        // Truyền danh sách công việc vào model
        model.addAttribute("suggestedJobs", suggestedJobs);
        return "candidate-screen/suggestJob";
    }

    @GetMapping("/profile")
    public String showCandidateForm(Principal principal, HttpSession session, Model model) {

        // Lấy thông tin tài khoản qua username
        Account account = accountRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Lưu accountId vào session
        Long accountId = account.getId();
        session.setAttribute("accountId", accountId);

        Candidate candidate = account.getCandidate();
        model.addAttribute("candidate", candidate);

        return "candidate-screen/profile";
    }
    @GetMapping("/home")
    public String showHome(Model model, Principal principal, HttpSession session) {
        if (principal == null) {
            return "redirect:/login"; // Chuyển hướng về trang đăng nhập nếu chưa đăng nhập
        }

        String username = principal.getName();
        model.addAttribute("username", username);

        // Nếu cần lấy thông tin khác từ session, sử dụng session.getAttribute
        Long accountId = (Long) session.getAttribute("accountId");
        if (accountId != null) {
            model.addAttribute("accountId", accountId);
        }

        List<Company> companies = companyService.findAll();
        List<Job> jobs = jobService.findAll();

        model.addAttribute("companies", companies);
        model.addAttribute("jobs", jobs);
        model.addAttribute("welcomeMessage", "Chào mừng bạn đến với Home!");

        return "candidate-screen/home";
    }

    @PostMapping("/jobs/{jobId}/apply")
    public String applyForJob(@PathVariable Long jobId, Principal principal, RedirectAttributes redirectAttributes, HttpSession session) {
        if (principal == null) {
            return "redirect:/login";
        }

        Long accountId = (Long) session.getAttribute("accountId");
        if (accountId == null) {
            return "redirect:/login";
        }

        try {
            String username = principal.getName();
            System.out.println("username: " + username);
            jobApplicationService.applyForJob(jobId, username);
            redirectAttributes.addFlashAttribute("successMessage", "Ứng tuyển thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ứng tuyển thất bại: " + e.getMessage());
        }
        return "redirect:/candidates/applied-jobs";
    }


    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal, HttpSession session) {
        // Lấy thông tin tài khoản từ session
        Long accountId = (Long) session.getAttribute("accountId");
        if (accountId == null) {
            throw new RuntimeException("Account ID không tồn tại trong session!");
        }

        // Lấy thông tin tài khoản từ database
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));

        // Lấy danh sách công việc đã nộp
        List<Job> appliedJobs = jobService.findByCandidates(account.getCandidate());

        // Thêm thông tin vào model
        model.addAttribute("username", principal.getName());
        model.addAttribute("candidate", account.getCandidate());
        model.addAttribute("appliedJobs", appliedJobs);

        return "candidate-screen/dashboard";
    }
    @GetMapping("/applied-jobs")
    public String showAppliedJobs(Model model, HttpSession session) {
        // Lấy thông tin tài khoản từ session
        Long accountId = (Long) session.getAttribute("accountId");
        if (accountId == null) {
            throw new RuntimeException("Account ID không tồn tại trong session!");
        }
        // Lấy thông tin tài khoản từ database
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));

        // Lấy danh sách công việc đã ứng tuyển
        List<Job> appliedJobs = jobService.findByCandidates(account.getCandidate());

        // Thêm thông tin vào model
        model.addAttribute("appliedJobs", appliedJobs);
        return "candidate-screen/listJob";
    }

}
