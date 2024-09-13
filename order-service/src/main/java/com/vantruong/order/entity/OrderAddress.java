package com.vantruong.order.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order_address")
public class OrderAddress {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "oa_id")
  private Long id;

  @Column(name = "contact_name")
  private String contactName;
  private String phone;
  private String address;

}
