package fit.vn.edu.iuh.frontend.controllers;

import fit.vn.edu.iuh.backend.models.Job;
import fit.vn.edu.iuh.backend.models.JobSkill;
import fit.vn.edu.iuh.backend.services.CompanyService;
import fit.vn.edu.iuh.backend.services.JobService;
import fit.vn.edu.iuh.backend.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private JobService jobService;
    @Autowired
    private SkillService skillService;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("lstCompany", companyService.findAll());
        model.addAttribute("lstJob", jobService.findAll());
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

    @GetMapping("/jobDetail")
    public String formDetailJob(@RequestParam("id") Long jobId, Model model){
        model.addAttribute("job", jobService.findById(jobId));
        return "job_screen/jobDetail";
    }
    @GetMapping("/detailCompany")
    public String detailCompany(@RequestParam("companyId") Long id, Model model) {
        model.addAttribute("company", companyService.findById(id));
        model.addAttribute("lstReview", companyService.getReviewsByCompanyId(id));
        model.addAttribute("lstJob", jobService.findListJobByCompanyId(id));
        //get List Skill By Job
        Map<Long, List<JobSkill>> lstMapJobSkill = new HashMap<>();
        for(Job job : jobService.findListJobByCompanyId(id)){
            List<JobSkill> lstJobSkill = skillService.findListSkillByJobId(job.getId());
            lstMapJobSkill.put(job.getId(), lstJobSkill);
        }
        model.addAttribute("lstMapJobSkill", lstMapJobSkill);
        return "company_screen/detailCompany";
    }
}
