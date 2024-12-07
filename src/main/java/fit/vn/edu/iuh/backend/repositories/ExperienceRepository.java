package fit.vn.edu.iuh.backend.repositories;

import fit.vn.edu.iuh.backend.models.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExperienceRepository extends JpaRepository<Experience, Long> , JpaSpecificationExecutor<Experience> {
  }