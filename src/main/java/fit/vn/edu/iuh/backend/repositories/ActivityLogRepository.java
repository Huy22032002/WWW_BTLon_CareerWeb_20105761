package fit.vn.edu.iuh.backend.repositories;

import fit.vn.edu.iuh.backend.models.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActivityLogRepository extends JpaRepository<ActivityLog, Long> , JpaSpecificationExecutor<ActivityLog> {
  }