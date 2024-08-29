package com.vantruong.order.entity;

import com.vantruong.order.enums.EPaymentMethod;
import com.vantruong.order.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import com.vantruong.order.enums.OrderStatus;

import java.util.ArrayList;
import java.util.List;

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

  @Column(name = "o_status")
  private OrderStatus orderStatus;

  @Column(name = "payment_status")
  private PaymentStatus paymentStatus;

  @Enumerated(EnumType.STRING)
  @ManyToOne
  @JoinColumn(name = "payment_method")
  private PaymentMethod paymentMethod;

  @Column(name = "address_id")
  private Integer addressId;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
  private List<OrderDetail> orderDetails = new ArrayList<>();
}
