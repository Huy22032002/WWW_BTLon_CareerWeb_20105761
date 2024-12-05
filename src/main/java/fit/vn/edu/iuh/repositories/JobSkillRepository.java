package fit.vn.edu.iuh.repositories;

import fit.vn.edu.iuh.models.JobSkill;
import fit.vn.edu.iuh.models.JobSkillId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillId> {
  }