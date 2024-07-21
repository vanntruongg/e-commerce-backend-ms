package paymentservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payment_method")
public class PaymentMethod {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int paymentMethodId;

  @Column(name = "name")
  private String name;

  @Column(name = "method")
  @Enumerated(EnumType.STRING)
  private paymentservice.enums.PaymentMethod method;

  @Column(name = "description")
  private String description;

  @Column
  private Boolean isActive;
}