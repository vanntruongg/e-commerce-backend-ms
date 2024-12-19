package com.vantruong.inventory.repository;

import com.vantruong.inventory.entity.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory, String> {

  Optional<Inventory> findInventoryByProductId(Long productId);

}
