package kz.kbtu.sf.orderservice.controller;

import jakarta.annotation.security.RolesAllowed;
import kz.kbtu.sf.orderservice.common.dto.OrderRequestDTO;
import kz.kbtu.sf.orderservice.entity.*;
import kz.kbtu.sf.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequestDTO orderRequest) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UUID customerId = currentUser.getId();

        Order createdOrder = orderService.createOrder(orderRequest, customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
}