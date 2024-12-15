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
    private AddressRepository addressRepository;
    @Autowired
    private ExperienceRepository experienceRepository;


    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }

    public Candidate save(Candidate candidate) {
        return candidateRepository.save(candidate);
    }
}