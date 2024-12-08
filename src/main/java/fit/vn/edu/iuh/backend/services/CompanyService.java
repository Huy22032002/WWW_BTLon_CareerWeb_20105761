package fit.vn.edu.iuh.backend.services;

import fit.vn.edu.iuh.backend.models.Company;
import fit.vn.edu.iuh.backend.models.CompanyReview;
import fit.vn.edu.iuh.backend.repositories.AddressRepository;
import fit.vn.edu.iuh.backend.repositories.CompanyRepository;
import fit.vn.edu.iuh.backend.repositories.CompanyReviewRepository;
import fit.vn.edu.iuh.backend.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyReviewRepository companyReviewRepository;
    @Autowired
    private AddressRepository addressRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
    public Company findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }
    public List<CompanyReview> getReviewsByCompanyId(Long id) {
        return companyReviewRepository.findByCompanyId(id);
    }
}
