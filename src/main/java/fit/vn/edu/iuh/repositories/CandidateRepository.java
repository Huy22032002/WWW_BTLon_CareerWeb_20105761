package fit.vn.edu.iuh.repositories;

import fit.vn.edu.iuh.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
}