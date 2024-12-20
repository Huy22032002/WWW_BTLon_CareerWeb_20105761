package vn.edu.iuh.fit.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.Candidate;
import vn.edu.iuh.fit.models.Job;
import vn.edu.iuh.fit.repositories.CandidateRepository;
import vn.edu.iuh.fit.repositories.JobRepository;

@Service
public class JobApplicationService {

    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;

    public JobApplicationService(CandidateRepository candidateRepository, JobRepository jobRepository) {
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
    }

    @Transactional
    public void applyForJob(Long jobId, String username) {
        Candidate candidate = candidateRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        candidate.getJobs().add(job);
        job.getCandidates().add(candidate);

        candidateRepository.save(candidate);
    }
}

