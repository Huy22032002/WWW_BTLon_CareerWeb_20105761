package vn.edu.iuh.fit.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.*;
import vn.edu.iuh.fit.repositories.*;

import java.util.List;
@Service
public class CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private CandidateSkillRepository candidateSkillRepository;

    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ExperienceRepository experienceRepository;


    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    public Candidate registerCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public void updateAddress(Long candidateId, Address address) {
        Candidate candidate = candidateRepository.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ứng viên"));
        address.setCandidate(candidate);
        addressRepository.save(address);
    }

    public Candidate save(Candidate candidate) {
        return candidateRepository.save(candidate);
    }
}