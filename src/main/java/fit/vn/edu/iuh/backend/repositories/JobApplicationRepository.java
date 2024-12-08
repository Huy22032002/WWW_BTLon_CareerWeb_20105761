package fit.vn.edu.iuh.backend.repositories;

import fit.vn.edu.iuh.backend.models.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> , JpaSpecificationExecutor<JobApplication> {
  }