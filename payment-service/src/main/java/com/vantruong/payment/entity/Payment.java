package com.vantruong.payment.entity;

import com.vantruong.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payment")
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer paymentId;
  private Double amount;

  @Enumerated(EnumType.STRING)
  private PaymentStatus status;

  @ManyToOne
  @JoinColumn(name = "method_id")
  private PaymentMethod method;
  private Integer orderId;
}
