package kz.kbtu.sf.orderpoller.service;

import kz.kbtu.sf.orderpoller.model.Outbox;
import kz.kbtu.sf.orderpoller.publisher.MessagePublisher;
import kz.kbtu.sf.orderpoller.repository.OutboxRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
@Slf4j
public class OrderPollerService {

    @Autowired
    private OutboxRepository repository;

    @Autowired
    private MessagePublisher messagePublisher;

    @Scheduled(fixedRate = 10000)
    public void pollOutboxMessagesAndPublish() {

        List<Outbox> unprocessedRecords = repository.findByProcessedFalse();

        log.info("unprocessed record count : {}", unprocessedRecords.size());


        unprocessedRecords.forEach(outbox -> {
            try {
                messagePublisher.publish(outbox.getPayload());
                outbox.setProcessed(true);
                repository.save(outbox);

            } catch (Exception ignored) {
                log.error(ignored.getMessage());
            }
        });
    }
}