package vn.edu.iuh.fit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.JobSkill;
import vn.edu.iuh.fit.models.JobSkillId;
import vn.edu.iuh.fit.models.Skill;
import vn.edu.iuh.fit.repositories.JobSkillRepository;
import vn.edu.iuh.fit.repositories.SkillRepository;

import java.util.List;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;

    public Skill save(Skill skill) {
        return skillRepository.save(skill);
    }
}
