package com.vantruong.payment.service;

import com.vantruong.payment.dto.PaymentDto;
import com.vantruong.payment.entity.Payment;

public interface PaymentService {
    boolean createPayment(PaymentDto paymentDto);
    Payment getPaymentByOrderId(int orderId);

//    boolean updateOrderPaymentStatusToPaid(int orderId);
}
