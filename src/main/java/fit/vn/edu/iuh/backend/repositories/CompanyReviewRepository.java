package fit.vn.edu.iuh.backend.repositories;

import fit.vn.edu.iuh.backend.models.CompanyReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompanyReviewRepository extends JpaRepository<CompanyReview, Long>, JpaSpecificationExecutor<CompanyReview> {
}