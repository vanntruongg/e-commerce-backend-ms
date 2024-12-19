package com.vantruong.user.repository;

import com.vantruong.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  Page<User> findByFirstNameContainingIgnoreCase(String firstName, Pageable pageable);
}
