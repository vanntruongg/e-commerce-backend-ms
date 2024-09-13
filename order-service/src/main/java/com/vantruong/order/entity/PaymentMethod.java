//package com.vantruong.order.model;
//
//import com.vantruong.order.model.enumeration.EPaymentMethod;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Entity
//@Table(name = "payment_method")
//public class PaymentMethod {
//  @Id
//  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @Column(name = "id")
//  private int id;
//
//  @Column(name = "method")
//  private String method;
//
//  @Column(name = "slug")
//  @Enumerated(EnumType.STRING)
//  private EPaymentMethod slug;
//
//  @Column(name = "description")
//  private String description;
//
//}
