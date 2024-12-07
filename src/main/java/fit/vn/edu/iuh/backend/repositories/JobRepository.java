package fit.vn.edu.iuh.backend.repositories;

import fit.vn.edu.iuh.backend.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobRepository extends JpaRepository<Job, Long> , JpaSpecificationExecutor<Job> {
  }