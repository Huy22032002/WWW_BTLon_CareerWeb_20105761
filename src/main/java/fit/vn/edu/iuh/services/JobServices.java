package fit.vn.edu.iuh.services;

import fit.vn.edu.iuh.models.Candidate;
import fit.vn.edu.iuh.models.Job;
import fit.vn.edu.iuh.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobServices {
    @Autowired
    private JobRepository jobRepository;

    private Job addJob(Job job) {
        return jobRepository.save(job);
    }

    private List<Job> getAllJob(Job job) {
        return jobRepository.findAll();
    }
    private Job getJobById(Long id){
        return jobRepository.findById(id).orElse(null);
    }

}
