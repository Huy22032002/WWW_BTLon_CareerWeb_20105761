package fit.vn.edu.iuh.services;

import fit.vn.edu.iuh.models.Skill;
import fit.vn.edu.iuh.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillServices {
    @Autowired
    private SkillRepository skillRepository;

    public Skill addSkill(Skill skill) {
        return skillRepository.save(skill);
    }
    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }
    public Skill getSkillById(Long id) {
        return skillRepository.findById(id).orElse(null);
    }
}
