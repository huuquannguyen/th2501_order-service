package com.example.order_service.repository;

import com.example.order_service.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByIdAndStatusEquals(Long id, String orderStatus);
}
