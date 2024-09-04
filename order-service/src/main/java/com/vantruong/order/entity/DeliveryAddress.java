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
@Table(name = "delivery_address")
public class DeliveryAddress {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "da_id")
  private int id;

  @Column(name = "name")
  private String name;

  @Column(name = "phone")
  private String phone;

  @Column(name = "address")
  private String address;

  @OneToOne
  @JoinColumn(name = "order_id", referencedColumnName = "order_id")
  @JsonIgnore
  private Order order;

}
