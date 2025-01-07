package com.vantruong.user.repository;

import com.vantruong.user.entity.User;
import com.vantruong.user.entity.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Integer> {

  List<UserAddress> findAllByUserEmailOrderByIsDefaultDesc(User user);
  List<UserAddress> findAllByUserEmail(User user);
  Optional<UserAddress> findByUserEmailAndIsDefault(User user, boolean isDefault);
}
