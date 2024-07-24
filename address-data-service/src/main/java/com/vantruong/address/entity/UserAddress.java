package com.vantruong.address.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserAddress {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String phone;
  private String street;

  @ManyToOne
  @JoinColumn(name = "ward_id")
  private AddressData ward;

  @ManyToOne
  @JoinColumn(name = "district_id")
  private AddressData district;

  @ManyToOne
  @JoinColumn(name = "province_id")
  private AddressData province;

  @Column(name = "is_default")
  private Boolean isDefault;

  @Column(name = "user_email")
  private String userEmail;
}
