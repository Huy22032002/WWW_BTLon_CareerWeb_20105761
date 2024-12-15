package vn.edu.iuh.fit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.*;
import vn.edu.iuh.fit.repositories.*;

import java.util.List;
import java.util.Optional;

@Service
public class JobService {

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyRepository companyRepository;

    public Job saveJob(Job job) {
        return jobRepository.save(job);
    }

    public Optional<Job> findJobById(Long id) {
        return jobRepository.findById(id);
    }

    public List<Job> getJobsByRecruiterUsername(String recruiterUsername) {
        Long companyId = companyRepository.findCompanyIdByRecruiterUsername(recruiterUsername);
        return jobRepository.findByCompanyId(companyId);
    }

    public List<Candidate> getCandidatesByJobId(Long jobId) {
        return jobRepository.findCandidatesByJobId(jobId);
    }

    public List<Job> findByCandidates(Candidate candidate) {
        if (candidate == null) {
            throw new IllegalArgumentException("Candidate không được null!");
        }
        return jobRepository.findByCandidates(candidate);
    }

    public List<Job> findAll() {
        return jobRepository.findAll();
    }
}
