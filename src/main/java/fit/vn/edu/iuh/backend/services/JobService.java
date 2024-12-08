package fit.vn.edu.iuh.backend.services;

import fit.vn.edu.iuh.backend.models.Job;
import fit.vn.edu.iuh.backend.models.JobSkill;
import fit.vn.edu.iuh.backend.repositories.JobRepository;
import fit.vn.edu.iuh.backend.repositories.JobSkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    @Autowired
    private JobRepository jobRepository;

    public List<Job> findAll() {
        return jobRepository.findAll();
    }
    public Job findById(Long id) {
        return jobRepository.findById(id).get();
    }
    public List<Job> findListJobByCompanyId(Long id) {
        return jobRepository.findListJobByCompanyId(id);
    }

}
