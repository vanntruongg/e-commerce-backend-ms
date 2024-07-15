package addressdataservice.repository;

import addressdataservice.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {

  List<UserAddress> findAllByUserEmailOrderByIsDefaultDesc(String email);
  List<UserAddress> findAllByUserEmail(String email);
  Optional<UserAddress> findByUserEmailAndIsDefault(String email, boolean isDefault);
}
