package com.vantruong.order.entity;

import com.vantruong.order.entity.enumeration.PaymentMethod;
import com.vantruong.order.entity.enumeration.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import com.vantruong.order.entity.enumeration.OrderStatus;

import java.util.Set;

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
  @Column(name = "order_id")
  private Long orderId;
  private String email;
  @Column(name = "total_price")
  private double totalPrice;
  private String notes;

  @Enumerated(EnumType.STRING)
  @Column(name = "order_status")
  private OrderStatus orderStatus;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_status")
  private PaymentStatus paymentStatus;

  @Column(name = "payment_method")
  @Enumerated(EnumType.STRING)
  private PaymentMethod paymentMethod;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "order_address_id", referencedColumnName = "oa_id")
  private OrderAddress orderAddress;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private Set<OrderItem> orderItems;
}
