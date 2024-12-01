package kz.kbtu.sf.orderservice.common.mapper;


import kz.kbtu.sf.orderservice.common.dto.OrderRequestDTO;
import kz.kbtu.sf.orderservice.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class OrderDTOtoEntityMapper {


    public Order map(OrderRequestDTO orderRequestDTO, UUID customerId) {
        return
                Order.builder()
                        .customerId(customerId)
                        .productId(orderRequestDTO.getProductId())
                        .name(orderRequestDTO.getName())
                        .productType(orderRequestDTO.getProductType())
                        .quantity(orderRequestDTO.getQuantity())
                        .price(orderRequestDTO.getPrice())
                        .orderDate(new Date())
                        .build();
    }
}
