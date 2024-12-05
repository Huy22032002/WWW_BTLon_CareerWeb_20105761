package fit.vn.edu.iuh.repositories;

import fit.vn.edu.iuh.models.Experience;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExperienceRepository extends JpaRepository<Experience, Long> {
  }