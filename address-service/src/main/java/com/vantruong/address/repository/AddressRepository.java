package com.vantruong.address.repository;

import com.vantruong.address.entity.AddressData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressData, Integer> {

  List<AddressData> findAllByParentCodeOrderByName(String parentCode);
}
