package fit.vn.edu.iuh.repositories;

import fit.vn.edu.iuh.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
  }