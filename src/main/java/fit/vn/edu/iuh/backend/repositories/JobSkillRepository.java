package fit.vn.edu.iuh.backend.repositories;

import fit.vn.edu.iuh.backend.models.JobSkill;
import fit.vn.edu.iuh.backend.models.JobSkillId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JobSkillRepository extends JpaRepository<JobSkill, JobSkillId>, JpaSpecificationExecutor<JobSkill> {
  public List<JobSkill> findListSkillByJobId(Long id);
}