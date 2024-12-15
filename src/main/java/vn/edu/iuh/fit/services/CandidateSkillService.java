package vn.edu.iuh.fit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.CandidateSkill;
import vn.edu.iuh.fit.models.Skill;
import vn.edu.iuh.fit.repositories.CandidateSkillRepository;

import java.util.List;

@Service
public class CandidateSkillService {
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    public List<Skill> findSkillsByCandidateId(Long candidateId) {
        return candidateSkillRepository.findSkillsByCandidateId(candidateId);
    }

    public void saveAll(List<CandidateSkill> candidateSkills) {
        candidateSkillRepository.saveAll(candidateSkills);
    }
}
