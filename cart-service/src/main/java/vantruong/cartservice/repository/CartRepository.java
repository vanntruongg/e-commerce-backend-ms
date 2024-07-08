package vantruong.cartservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import vantruong.cartservice.entity.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, String> {
}
