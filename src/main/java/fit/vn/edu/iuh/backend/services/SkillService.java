package fit.vn.edu.iuh.backend.services;

import fit.vn.edu.iuh.backend.models.JobSkill;
import fit.vn.edu.iuh.backend.repositories.JobSkillRepository;
import fit.vn.edu.iuh.backend.repositories.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {
    @Autowired
    private SkillRepository skillRepository;
    @Autowired
    private JobSkillRepository jobSkillRepository;
    public List<JobSkill> findListSkillByJobId(Long id) {
        return jobSkillRepository.findListSkillByJobId(id);
    }
}
