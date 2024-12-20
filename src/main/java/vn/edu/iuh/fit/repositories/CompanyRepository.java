package vn.edu.iuh.fit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.models.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    @Query("SELECT c.id FROM Company c JOIN c.account a WHERE a.username = :recruiterUsername")
    Long findCompanyIdByRecruiterUsername(@Param("recruiterUsername") String recruiterUsername);
}