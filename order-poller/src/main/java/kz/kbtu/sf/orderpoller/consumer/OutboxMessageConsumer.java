package kz.kbtu.sf.orderpoller.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OutboxMessageConsumer{

    @KafkaListener(topics = "unprocessed-order-events",groupId = "jt-group")
    public void consume(String payload){
        log.info("Event consumed {} ",payload);
    }
}
