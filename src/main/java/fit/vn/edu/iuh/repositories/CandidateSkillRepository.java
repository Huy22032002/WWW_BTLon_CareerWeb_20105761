package fit.vn.edu.iuh.repositories;

import fit.vn.edu.iuh.models.CandidateSkill;
import fit.vn.edu.iuh.models.CandidateSkillId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, CandidateSkillId> {
  }