package com.vantruong.order.entity;

import jakarta.persistence.*;
import lombok.*;
import com.vantruong.order.enums.OrderStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "o_id")
  private int orderId;

  @Column(name = "email")
  private String email;

  @Column(name = "o_notes")
  private String notes;

  @Column(name = "O_total_price")
  private double totalPrice;

  @Builder.Default
  @Column(name = "o_status")
  private OrderStatus orderStatus = OrderStatus.PENDING_CONFIRM;

  @Column(name = "address_id")
  private Integer addressId;
}
