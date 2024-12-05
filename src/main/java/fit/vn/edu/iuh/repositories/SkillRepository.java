package fit.vn.edu.iuh.repositories;

import fit.vn.edu.iuh.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Skill, Long> {
  }