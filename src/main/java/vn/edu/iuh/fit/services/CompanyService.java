package vn.edu.iuh.fit.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.Company;
import vn.edu.iuh.fit.repositories.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public Company findCompanyById(Long companyId) {
        Optional<Company> company = companyRepository.findById(companyId);
        return company.orElse(null);
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
}
