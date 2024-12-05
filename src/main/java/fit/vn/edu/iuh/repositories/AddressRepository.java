package fit.vn.edu.iuh.repositories;

import fit.vn.edu.iuh.models.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}