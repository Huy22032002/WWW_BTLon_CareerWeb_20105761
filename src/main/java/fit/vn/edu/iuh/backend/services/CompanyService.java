package fit.vn.edu.iuh.backend.services;

import fit.vn.edu.iuh.backend.models.Company;
import fit.vn.edu.iuh.backend.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
}
