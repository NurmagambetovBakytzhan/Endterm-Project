package kz.kbtu.sf.orderservice.service;


import jakarta.transaction.Transactional;
import kz.kbtu.sf.orderservice.common.dto.OrderRequestDTO;
import kz.kbtu.sf.orderservice.common.mapper.OrderDTOtoEntityMapper;
import kz.kbtu.sf.orderservice.common.mapper.OrderEntityToOutboxEntityMapper;
import kz.kbtu.sf.orderservice.entity.Order;
import kz.kbtu.sf.orderservice.entity.Outbox;
import kz.kbtu.sf.orderservice.repository.OrderRepository;
import kz.kbtu.sf.orderservice.repository.OutboxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderDTOtoEntityMapper orderDTOtoEntityMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private OrderEntityToOutboxEntityMapper orderEntityToOutboxEntityMapper;


    @Transactional
    public Order createOrder(OrderRequestDTO orderRequestDTO, UUID customerId) {

        Order order = orderDTOtoEntityMapper.map(orderRequestDTO, customerId);
        order = orderRepository.save(order);

        Outbox outbox = orderEntityToOutboxEntityMapper.map(order);
        outboxRepository.save(outbox);

        return order;
    }
}
