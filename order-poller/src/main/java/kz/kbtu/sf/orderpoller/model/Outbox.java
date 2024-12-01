package kz.kbtu.sf.orderpoller.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "outbox")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Outbox {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long UUID;
    private UUID OrderId;
    private String payload;
    private Date createdAt;
    private Boolean processed;
}