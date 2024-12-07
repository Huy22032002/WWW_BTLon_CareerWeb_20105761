package fit.vn.edu.iuh.backend.repositories;

import fit.vn.edu.iuh.backend.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AddressRepository extends JpaRepository<Address, Long> , JpaSpecificationExecutor<Address> {
  }