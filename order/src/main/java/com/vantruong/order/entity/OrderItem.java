package com.vantruong.order.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_item")
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "od_id")
  private int orderItemId;

  @Column(name = "quantity")
  private int quantity;

  @Column(name = "product_id")
  private Long productId;

  @Column(name = "product_name")
  private String productName;

  @Column(name = "product_price")
  private Double productPrice;

  @Column(name = "product_image")
  private String productImage;

  @Column(name = "product_size")
  private String productSize;

  @ManyToOne
  @JoinColumn(name = "order_id", referencedColumnName = "order_id")
  @JsonIgnore
  private Order order;

}
