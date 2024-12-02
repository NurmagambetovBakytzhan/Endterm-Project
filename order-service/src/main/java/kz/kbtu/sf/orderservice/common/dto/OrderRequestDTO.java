package kz.kbtu.sf.orderservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Details of an order request")
public class OrderRequestDTO {

    @Schema(description = "Name of the customer placing the order", example = "John Doe")
    private String name;

    @Schema(description = "Unique identifier for the order", example = "123e4567-e89b-12d3-a456-426614174000")
    private UUID orderId;

    @Schema(description = "Unique identifier for the product being ordered", example = "321e4567-e89b-12d3-a456-426614174111")
    private UUID productId;

    @Schema(description = "Type of the product", example = "Electronics")
    private String productType;

    @Schema(description = "Quantity of the product being ordered", example = "3")
    private int quantity;

    @Schema(description = "Price of the product", example = "99.99")
    private BigDecimal price;
}
