package fit.vn.edu.iuh.services;

import fit.vn.edu.iuh.models.Candidate;
import fit.vn.edu.iuh.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CandidateServices {
    @Autowired
    private CandidateRepository candidateRepository;

    public Candidate addCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }

    public Candidate getCandidateById(Long id) {
        return candidateRepository.findById(id).orElse(null);
    }
}
