package fit.vn.edu.iuh.backend.repositories;

import fit.vn.edu.iuh.backend.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SkillRepository extends JpaRepository<Skill, Long> , JpaSpecificationExecutor<Skill> {
  }