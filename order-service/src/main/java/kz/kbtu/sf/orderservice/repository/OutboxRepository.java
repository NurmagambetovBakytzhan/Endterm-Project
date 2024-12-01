package kz.kbtu.sf.orderservice.repository;

import kz.kbtu.sf.orderservice.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxRepository extends JpaRepository<Outbox, UUID> {
}
