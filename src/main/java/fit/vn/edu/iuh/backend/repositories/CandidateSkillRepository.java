package fit.vn.edu.iuh.backend.repositories;

import fit.vn.edu.iuh.backend.models.CandidateSkill;
import fit.vn.edu.iuh.backend.models.CandidateSkillId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CandidateSkillRepository extends JpaRepository<CandidateSkill, CandidateSkillId> , JpaSpecificationExecutor<CandidateSkill> {
  }