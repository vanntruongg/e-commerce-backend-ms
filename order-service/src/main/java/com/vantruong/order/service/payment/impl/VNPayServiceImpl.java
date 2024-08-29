package com.vantruong.order.service.payment.impl;

import com.vantruong.order.config.VNPayConfig;
import com.vantruong.order.enums.OrderStatus;
import com.vantruong.order.producer.KafkaProducer;
import com.vantruong.order.service.order.OrderService;
import com.vantruong.order.service.payment.VNPayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class VNPayServiceImpl implements VNPayService {
  private final OrderService orderService;
  private final KafkaProducer kafkaProducer;
  @Override
  public Boolean handleVNPayCallBack(Map<String, String> params) {
    if (verifySignature(params)) {
      if ("00".equals(params.get("vnp_ResponseCode"))) {
        log.info("Thanh toán với VN PAY");
        return true;
      }
    }
    return false;
  }

  private boolean verifySignature(Map<String, String> params) {
    String receivedSignature = params.get("vnp_SecureHash");
    params.remove("vnp_SecureHash");

    String recalculatedSignature = VNPayConfig.hashAllFields(params);

    return receivedSignature.equals(recalculatedSignature);

  }

  @Override
  public String generatePaymentUrl(Integer orderId, double amount) {
    String vnp_Version = "2.1.0";
    String vnp_Command = "pay";
    String orderType = "other";

//    String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
    String vnp_TxnRef = orderId.toString();
    String vnp_IpAddr = VNPayConfig.getIpAddress();

    String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", vnp_Version);
    vnp_Params.put("vnp_Command", vnp_Command);
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_Amount", String.valueOf((long) amount * 100));
    vnp_Params.put("vnp_CurrCode", "VND");
    vnp_Params.put("vnp_BankCode", "NCB");

    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang: " + vnp_TxnRef);
    vnp_Params.put("vnp_OrderType", orderType);
    vnp_Params.put("vnp_Locale", "vn");

    vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

    cld.add(Calendar.MINUTE, 5);
    String vnp_ExpireDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

    List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator<String> itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = itr.next();
      String fieldValue = vnp_Params.get(fieldName);
      if ((fieldValue != null) && (!fieldValue.isEmpty())) {
        //Build hash data
        hashData.append(fieldName);
        hashData.append('=');
        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
        //Build query
        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
        query.append('=');
        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        }
      }
    }
    String queryUrl = query.toString();
    String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
    queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
    return VNPayConfig.vnp_PayUrl + "?" + queryUrl;
  }

}
