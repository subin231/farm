package com.farmstory.repository.market;

import com.farmstory.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>, CartRepositoryCustom {

    public List<Cart> findCartByUserId(String userId);
    public Cart findCartByCartNo(int cartNo);

}
