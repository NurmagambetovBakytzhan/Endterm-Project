package kz.kbtu.sf.orderservice.common.mapper;


import com.fasterxml.jackson.databind.ObjectMapper;
import kz.kbtu.sf.orderservice.entity.Order;
import kz.kbtu.sf.orderservice.entity.Outbox;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class OrderEntityToOutboxEntityMapper {


    @SneakyThrows
    public Outbox map(Order order) {
        return
                Outbox.builder()
                        .orderId(order.getId())
                        .payload(new ObjectMapper().writeValueAsString(order))
                        .createdAt(new Date())
                        .processed(false)
                        .build();
    }
}
