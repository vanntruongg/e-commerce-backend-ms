package com.vantruong.cart.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.vantruong.cart.entity.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, String> {
}
