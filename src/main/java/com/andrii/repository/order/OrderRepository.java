package com.andrii.repository.order;

import com.andrii.model.Order;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("""
            select distinct o
            from Order o
            left join fetch o.orderItems oi
            left join fetch oi.book
            where o.user.id = :userId
            """)
    Page<Order> findAllByUserId(
            Long userId,
            Pageable pageable
    );

    @Query("""
            select distinct o
            from Order o
            left join fetch o.orderItems oi
            left join fetch oi.book
            where o.id = :orderId and o.user.id = :userId
            """)
    Optional<Order> findByIdAndUserId(
            Long orderId,
            Long userId
    );
}
