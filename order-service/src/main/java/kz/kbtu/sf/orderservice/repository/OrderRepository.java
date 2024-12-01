package kz.kbtu.sf.orderservice.repository;

import kz.kbtu.sf.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
