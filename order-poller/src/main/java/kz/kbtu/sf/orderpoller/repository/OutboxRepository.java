package kz.kbtu.sf.orderpoller.repository;

import kz.kbtu.sf.orderpoller.model.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {
    List<Outbox> findByProcessedFalse();
}



