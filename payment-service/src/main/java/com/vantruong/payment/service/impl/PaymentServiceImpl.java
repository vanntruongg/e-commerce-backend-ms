package com.vantruong.payment.service.impl;

import com.vantruong.payment.constant.MessageConstant;
import com.vantruong.payment.dto.PaymentDto;
import com.vantruong.payment.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vantruong.payment.entity.Payment;
import com.vantruong.payment.entity.PaymentMethod;
import com.vantruong.payment.exception.NotFoundException;
import com.vantruong.payment.repository.PaymentRepository;
import com.vantruong.payment.service.PaymentMethodService;
import com.vantruong.payment.service.PaymentService;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
  private final PaymentRepository paymentRepository;
  private final PaymentMethodService paymentMethodService;
  @Override
  @Transactional
  public boolean createPayment(PaymentDto paymentDto) {
    PaymentMethod paymentMethod = paymentMethodService.findById(paymentDto.getPaymentId());
    Payment payment = Payment.builder()
            .orderId(paymentDto.getOrderId())
            .method(paymentMethod)
            .status(paymentDto.getStatus())
            .amount(paymentDto.getAmount())
            .build();
    paymentRepository.save(payment);
    return true;
  }

  @Override
  public Payment getPaymentByOrderId(int orderId) {
    return paymentRepository.findByOrderId(orderId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.NOT_FOUND));
  }

//  @Override
//  public boolean updateOrderPaymentStatusToPaid(int orderId) {
//    Payment payment = getPaymentByOrderId(orderId);
//    payment.setStatus(PaymentStatus.PAID);
//    paymentRepository.save(payment);
//    return true;
//  }
}
