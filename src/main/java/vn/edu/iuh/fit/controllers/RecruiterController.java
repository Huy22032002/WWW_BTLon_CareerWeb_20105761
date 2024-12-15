package vn.edu.iuh.fit.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.iuh.fit.enums.SkillLevelType;
import vn.edu.iuh.fit.enums.SkillType;
import vn.edu.iuh.fit.models.*;
import vn.edu.iuh.fit.services.*;
import vn.edu.iuh.fit.models.*;
import vn.edu.iuh.fit.services.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/recruiter")
public class RecruiterController {

    @Autowired
    private JobService jobService;
    @Autowired
    private EmailLogService emailLogService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private SkillService skillService;
    @Autowired
    private JobSkillService jobSkillService;
    // Trang quản lý công việc
    @GetMapping("dashboard/jobs")
    public String listJobs(Model model, Principal principal) {
        String recruiterUsername = principal.getName();
        List<Job> jobs = jobService.getJobsByRecruiterUsername(recruiterUsername);
        model.addAttribute("jobs", jobs);
        return "recruiters/jobs";
    }
    @GetMapping("dashboard/jobs/{jobId}/candidates")
    public String listCandidates(@PathVariable Long jobId, Model model, Principal principal) {
        // Lấy thông tin công việc
        Optional<Job> job = jobService.findJobById(jobId);

        if (job.isEmpty()) {
            throw new IllegalArgumentException("Công việc không tồn tại");
        }

        // Lấy danh sách ứng viên cho công việc
        List<Candidate> candidates = jobService.getCandidatesByJobId(jobId);

        // Thêm dữ liệu vào model
        model.addAttribute("job", job.get());
        model.addAttribute("candidates", candidates);

        return "recruiters/job-candidates";
    }

    @GetMapping("/dashboard")
    public String viewDashboard(Model model, Principal principal) {
        String recruiterUsername = principal.getName();
//        Company company = accountService.getCompanyByRecruiterUsername(recruiterUsername); // Lấy thông tin công ty
        List<Job> jobs = jobService.getJobsByRecruiterUsername(recruiterUsername); // Lấy danh sách công việc

        // Đưa dữ liệu vào model
        model.addAttribute("recruiterUsername", recruiterUsername);
//        model.addAttribute("company", company);
        model.addAttribute("jobs", jobs);

        return "recruiters/dashboard";
    }
    @GetMapping("/dashboard/jobs/{jobId}/candidates/sent-emails")
    public String viewSentEmails(@PathVariable Long jobId, Model model) {
        try {
            // Lấy danh sách email đã gửi từ cơ sở dữ liệu
            List<EmailLog> sentEmails = emailLogService.getEmailLogsByJobId(jobId);

            if (sentEmails.isEmpty()) {
                model.addAttribute("error", "Không có ứng viên nào đã được gửi email cho công việc này.");
            } else {
                model.addAttribute("sentEmails", sentEmails);
            }

            model.addAttribute("jobId", jobId);
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi truy xuất danh sách email: " + e.getMessage());
        }

        return "recruiters/sentEmails";
    }
    // Lưu công việc mới
    @GetMapping("/dashboard/jobs/new")
    public String showJobForm(Model model, Principal principal) {
        String username = principal.getName();
        Optional<Account> account = accountService.findByUsername(username);

        if (account.isEmpty()) {
            throw new IllegalStateException("Không tìm thấy tài khoản.");
        }

        Company currentCompany = account.get().getCompany();

        if (currentCompany == null) {
            throw new IllegalStateException("Tài khoản không liên kết với công ty.");
        }

        Job job = new Job();
        job.setCompany(currentCompany);

        model.addAttribute("job", job);
        model.addAttribute("skillLevels", List.of(
                SkillLevelType.BEGINNER,
                SkillLevelType.INTERMEDIATE,
                SkillLevelType.ADVANCED,
                SkillLevelType.PROFESSIONAL,
                SkillLevelType.EXPERT
        ));
        model.addAttribute("skillTypes", List.of(
                SkillType.SOFT_SKILL,
                SkillType.UNSPECIFIC,
                SkillType.TECHNICAL_SKILL
        ));

        return "recruiters/new-job";
    }

        @PostMapping("/dashboard/jobs/new")
    public String saveJob(
            Principal principal,
            @ModelAttribute Job job,
            @RequestParam("skillName[]") List<String> skillNames,
            @RequestParam("skillType[]") List<SkillType> skillTypes,
            @RequestParam("skillDesc[]") List<String> skillDescs,
            @RequestParam("skillLevel[]") List<SkillLevelType> skillLevels,
            @RequestParam("moreInfos[]") List<String> moreInfos,
            HttpSession session) {

        // Lấy accountId từ session
        Long accountId = (Long) session.getAttribute("accountId");
        if (accountId == null) {
            throw new IllegalStateException("Không tìm thấy accountId trong session.");
        }

        // Lấy thông tin Account từ accountId
        Optional<Account> accountOpt = accountService.findByUsername(principal.getName());
        if (accountOpt.isEmpty()) {
            throw new IllegalStateException("Không tìm thấy tài khoản.");
        }

        Account account = accountOpt.get();
        Company currentCompany = account.getCompany();
        if (currentCompany == null) {
            throw new IllegalStateException("Tài khoản không liên kết với công ty.");
        }

        // Gán công ty cho công việc
        job.setCompany(currentCompany);
        jobService.saveJob(job);

        // Lưu các kỹ năng
        for (int i = 0; i < skillNames.size(); i++) {
            Skill skill = new Skill();
            skill.setSkillName(skillNames.get(i));
            skill.setSkillDescription(skillDescs.get(i));
            skill.setType(skillTypes.get(i));
            skillService.save(skill);

            JobSkillId id = new JobSkillId();
            id.setJobId(job.getId());
            id.setSkillId(skill.getId());

            JobSkill jobSkill = new JobSkill();
            jobSkill.setId(id);
            jobSkill.setJob(job);
            jobSkill.setSkill(skill);
            jobSkill.setSkillLevel(skillLevels.get(i));
            jobSkill.setMoreInfos(moreInfos.get(i));
            jobSkillService.save(jobSkill);
        }

        return "redirect:/recruiter/dashboard/jobs";
    }
    @GetMapping("/dashboard/jobs/{jobId}/skills")
    public String viewSkills(@PathVariable Long jobId, Model model) {

        List<JobSkill> jobSkills = jobSkillService.findSkillsByJobId(jobId);
        model.addAttribute("jobSkills", jobSkills);
        return "recruiters/job-skills";
    }

}
