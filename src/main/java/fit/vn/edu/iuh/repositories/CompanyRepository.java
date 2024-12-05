package fit.vn.edu.iuh.repositories;

import fit.vn.edu.iuh.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
  }