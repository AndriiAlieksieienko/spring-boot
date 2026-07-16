package com.andrii.repository.cart;

import com.andrii.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("""
            select distinct sc
            from ShoppingCart sc
            left join fetch sc.cartItems ci
            left join fetch ci.book
            where sc.user.id = :userId
            """)
    Optional<ShoppingCart> findByUserId(Long userId);
}
