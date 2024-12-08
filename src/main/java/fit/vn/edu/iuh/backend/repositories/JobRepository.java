package fit.vn.edu.iuh.backend.repositories;

import fit.vn.edu.iuh.backend.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> , JpaSpecificationExecutor<Job> {
  public List<Job> findListJobByCompanyId(Long id);
}