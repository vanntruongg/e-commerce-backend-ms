package com.vantruong.order.service.payment.impl;

import com.vantruong.common.exception.ErrorCode;
import com.vantruong.common.exception.NotFoundException;
import com.vantruong.order.constant.MessageConstant;
import com.vantruong.order.dto.PaymentMethodDto;
import com.vantruong.order.entity.PaymentMethod;
import com.vantruong.order.repository.PaymentMethodRepository;
import com.vantruong.order.service.payment.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
  private final PaymentMethodRepository paymentMethodRepository;

  @Override
  public PaymentMethod findById(int paymentMethodId) {
    return paymentMethodRepository.findById(paymentMethodId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.PAYMENT_METHOD_NOT_FOUND));
  }

  @Override
  public List<PaymentMethodDto> findAll() {
    List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();

    return paymentMethods.stream()
            .map(paymentMethod -> PaymentMethodDto.builder()
                    .id(paymentMethod.getId())
                    .method(paymentMethod.getMethod())
                    .slug(paymentMethod.getSlug())
                    .description(paymentMethod.getDescription())
                    .build())
            .toList();
  }

}
