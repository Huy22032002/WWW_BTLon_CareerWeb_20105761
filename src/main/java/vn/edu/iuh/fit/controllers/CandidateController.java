package vn.edu.iuh.fit.controllers;

import org.antlr.v4.runtime.misc.LogManager;
import vn.edu.iuh.fit.repositories.*;
import vn.edu.iuh.fit.services.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.iuh.fit.enums.SkillLevelType;
import vn.edu.iuh.fit.models.*;
import com.neovisionaries.i18n.CountryCode;

import java.security.Principal;
import java.time.format.DateTimeFormatter;
import java.util.*;
import vn.edu.iuh.fit.dtos.CandidateFullInfoDTO;
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
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private AddressService addressService;

    @GetMapping("/list")
    public String showCandidateList(Model model) {
        model.addAttribute("candidates", candidateServices.findAll());
        return "candidates/candidates";
    }

    @PostMapping("/jobs/{jobId}/apply")
    public String applyForJob(@PathVariable Long jobId, Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null || principal.getName().equals("anonymousUser")) {
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
        return "candidates/suggestJob";
    }

    @PostMapping("/profile")
    public String updateProfile(
            @ModelAttribute("candidateFullInfoDTO") CandidateFullInfoDTO candidateFullInfoDTO,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        // Lấy accountId từ session
        Long accountId = (Long) session.getAttribute("accountId");
        if (accountId == null) {
            return "redirect:/login"; // Chuyển hướng nếu chưa đăng nhập
        }

        // Lấy thông tin tài khoản
        Account account = accountService.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại!"));

        // Lấy hoặc tạo mới Candidate
        Candidate candidate = account.getCandidate();
        if (candidate == null) {
            candidate = new Candidate();
        }

        // Cập nhật thông tin ứng viên
        candidate.setFullName(candidateFullInfoDTO.getCandidate().getFullName());
        candidate.setDob(candidateFullInfoDTO.getCandidate().getDob());
        candidate.setEmail(candidateFullInfoDTO.getCandidate().getEmail());
        candidate.setPhone(candidateFullInfoDTO.getCandidate().getPhone());

        // Cập nhật địa chỉ
        Address address = candidateFullInfoDTO.getAddress();
        Address savedAddress = addressService.save(address);
        candidate.setAddress(savedAddress);

        // Lưu thông tin ứng viên
        Candidate savedCandidate = candidateService.save(candidate);
        account.setCandidate(savedCandidate);
        accountService.save(account);

        // Cập nhật danh sách kỹ năng
        List<CandidateSkill> candidateSkills = candidateFullInfoDTO.getCandidateSkills();
        candidateSkills.forEach(skill -> skill.setCandidate(savedCandidate));
        candidateSkillService.saveAll(candidateSkills);

        // Thông báo thành công
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật hồ sơ thành công!");

        return "redirect:/candidates/dashboard";
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

        return "candidates/dashboard";
    }
    @GetMapping("/applied-jobs")
    public String showAppliedJobs(Model model, Principal principal, HttpSession session) {
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
        return "candidates/candidate-jobs"; // Tên file view cho danh sách công việc đã ứng tuyển
    }
    @GetMapping("/profile")
    public String showCandidateForm(Principal principal, HttpSession session, Model model) {
        // Lấy danh sách quốc gia
        List<String> countries = Arrays.stream(CountryCode.values())
                .map(CountryCode::getName)
                .filter(Objects::nonNull)
                .sorted()
                .toList();
        model.addAttribute("countries", countries);

        // Lấy thông tin tài khoản qua username
        Account account = accountRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // Lưu accountId vào session
        Long accountId = account.getId();
        session.setAttribute("accountId", accountId);

        // Kiểm tra trạng thái cập nhật hồ sơ
        boolean isProfileUpdated = account.getCandidate() != null;
        model.addAttribute("isProfileUpdated", isProfileUpdated);

        // Định dạng ngày sinh
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Candidate candidate = isProfileUpdated ? account.getCandidate() : new Candidate();
        String formattedDob = candidate.getDob() != null ? candidate.getDob().format(formatter) : "";
        model.addAttribute("formattedDob", formattedDob);

        // Gán dữ liệu mặc định nếu chưa cập nhật
        CandidateFullInfoDTO candidateFullInfoDTO = new CandidateFullInfoDTO();
        if (isProfileUpdated) {
            candidateFullInfoDTO.setCandidate(account.getCandidate());
            candidateFullInfoDTO.setAddress(account.getCandidate().getAddress());
            candidateFullInfoDTO.setExperiences(account.getCandidate().getExperiences());
            candidateFullInfoDTO.setCandidateSkills(account.getCandidate().getCandidateSkills());
        } else {
            candidateFullInfoDTO.setCandidate(new Candidate());
            candidateFullInfoDTO.setAddress(new Address());
            candidateFullInfoDTO.setExperiences(Collections.singletonList(new Experience()));
            candidateFullInfoDTO.setCandidateSkills(Collections.singletonList(new CandidateSkill()));
        }

        // Thêm dữ liệu vào model
        model.addAttribute("candidateFullInfoDTO", candidateFullInfoDTO);
        model.addAttribute("skills", skillService.findAll());
        model.addAttribute("skillLevels", List.of(SkillLevelType.BEGINNER, SkillLevelType.INTERMEDIATE, SkillLevelType.EXPERT));

        return "candidates/candidate-info";
    }
    @GetMapping("/home")
    public String showHome(Model model, Principal principal, HttpSession session) {
        // Kiểm tra người dùng đã đăng nhập chưa
        if (principal == null) {
            return "redirect:/login"; // Chuyển hướng về trang đăng nhập nếu chưa đăng nhập
        }

        // Lấy thông tin username từ Principal
        String username = principal.getName();
        model.addAttribute("username", username); // Đưa thông tin người dùng vào Model

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

        return "candidates/home";
    }

}
