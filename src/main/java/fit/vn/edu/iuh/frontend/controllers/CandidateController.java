package fit.vn.edu.iuh.frontend.controllers;

import fit.vn.edu.iuh.backend.services.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;
    @GetMapping("/listCandidate")
    public String listCandidates(Model model) {
        model.addAttribute("lstCandidate", candidateService.findAll());
        return "candidate_screen/listCandidate";
    }
}
