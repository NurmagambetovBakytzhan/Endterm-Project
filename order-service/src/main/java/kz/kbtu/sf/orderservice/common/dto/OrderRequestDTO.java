package kz.kbtu.sf.orderservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequestDTO {

    private String name;
    private UUID orderId;
    private UUID productId;
    private String productType;
    private int quantity;
    private BigDecimal price;
}
