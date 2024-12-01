package kz.kbtu.sf.orderservice.common.mapper;


import kz.kbtu.sf.orderservice.common.dto.OrderRequestDTO;
import kz.kbtu.sf.orderservice.entity.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class OrderDTOtoEntityMapper {


    public Order map(OrderRequestDTO orderRequestDTO) {
        return
                Order.builder()
//                        .customerId(Alikhan's get token)
                        .productId(orderRequestDTO.getProductId())
                        .name(orderRequestDTO.getName())
                        .productType(orderRequestDTO.getProductType())
                        .quantity(orderRequestDTO.getQuantity())
                        .price(orderRequestDTO.getPrice())
                        .orderDate(new Date())
                        .build();
    }
}
