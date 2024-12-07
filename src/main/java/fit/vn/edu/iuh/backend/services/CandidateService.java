package fit.vn.edu.iuh.backend.services;

import fit.vn.edu.iuh.backend.models.Candidate;
import fit.vn.edu.iuh.backend.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateService {
    @Autowired
    private CandidateRepository candidateRepository;

    public List<Candidate> findAll() {
        return candidateRepository.findAll();
    }
}
