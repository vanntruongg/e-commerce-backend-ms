package com.vantruong.payment.service.impl;

import com.vantruong.payment.constant.MessageConstant;
import com.vantruong.payment.dto.PaymentMethodDto;
import com.vantruong.payment.entity.PaymentMethod;
import com.vantruong.payment.exception.ErrorCode;
import com.vantruong.payment.exception.NotFoundException;
import com.vantruong.payment.service.PaymentMethodService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import com.vantruong.payment.repository.PaymentMethodRepository;

import java.lang.reflect.Type;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodServiceImpl implements PaymentMethodService {
  private final PaymentMethodRepository paymentMethodRepository;
  private final ModelMapper modelMapper;

  @Override
  public PaymentMethod findById(int paymentMethodId) {
    return paymentMethodRepository.findById(paymentMethodId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND, MessageConstant.PAYMENT_METHOD_NOT_FOUND));
  }

  @Override
  public List<PaymentMethodDto> findAll() {
    List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
    Type typeToken = new TypeToken<List<PaymentMethodDto>>() {}.getType();
//    return paymentMethods.stream()
//            .map(paymentMethod -> modelMapper.map(paymentMethod, PaymentMethodDto.class))
//            .collect(Collectors.toList());
    return modelMapper.map(paymentMethods, typeToken);
  }

}
