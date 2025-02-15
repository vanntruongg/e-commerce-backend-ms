package com.vantruong.order.config;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Component
public class VNPayConfig {
  public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
  public static String vnp_ReturnUrl = "http://localhost:9000/api/v1/order-orchestrator/vnpay-callback";
  public static String vnp_TmnCode = "FIPEWQ2U";
  public static String vnp_Version = "2.1.0";
  public static String vnp_Command = "pay";
  public static String orderType = "other";
  public static String secretKey = "BZGAUDLOPYWCAAPMJIAEMEWZBWSKMGNX";
  public static String vnp_ApiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/api/transaction";


  private static byte[] getBytesAfterHashValue(String message) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    return md.digest(message.getBytes(StandardCharsets.UTF_8));
  }
  public static String md5(String message) {
    String digest = null;
    try {
      byte[] hash =  getBytesAfterHashValue(message);
      StringBuilder sb = new StringBuilder(2 * hash.length);
      for (byte b : hash) {
        sb.append(String.format("%02x", b & 0xff));
      }
      digest = sb.toString();
    } catch (NoSuchAlgorithmException ex) {
      digest = "";
    }
    return digest;
  }

  public static String Sha256(String message) {
    String digest = null;
    try {
      byte[] hash = getBytesAfterHashValue(message);
      StringBuilder sb = new StringBuilder(2 * hash.length);
      for (byte b : hash) {
        sb.append(String.format("%02x", b & 0xff));
      }
      digest = sb.toString();
    } catch (NoSuchAlgorithmException ex) {
      digest = "";
    }
    return digest;
  }

  // Util for VNPAY
  public static String hashAllFields(Map<String, String> fields) {
    List<String> fieldNames = new ArrayList<>(fields.keySet());
    Collections.sort(fieldNames);
    StringBuilder sb = new StringBuilder();
    Iterator<String> itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = itr.next();
      String fieldValue = fields.get(fieldName);
      if ((fieldValue != null) && (!fieldValue.isEmpty())) {
        sb.append(fieldName);
        sb.append("=");
//        sb.append(fieldValue);
        sb.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
      }
      if (itr.hasNext()) {
        sb.append("&");
      }
    }
    return hmacSHA512(secretKey, sb.toString());
  }

  public static String hmacSHA512(final String key, final String data) {
    try {
      if (key == null || data == null) {
        throw new NullPointerException();
      }
      final Mac hmac512 = Mac.getInstance("HmacSHA512");
      byte[] hmacKeyBytes = key.getBytes(StandardCharsets.UTF_8);
      final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
      hmac512.init(secretKey);
      byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
      byte[] result = hmac512.doFinal(dataBytes);
      StringBuilder sb = new StringBuilder(2 * result.length);
      for (byte b : result) {
        sb.append(String.format("%02x", b & 0xff));
      }
      return sb.toString();
    } catch (Exception ex) {
      return "";
    }
  }

  public static String getIpAddress() {
    return "YOUR_IP_ADDRESS"; // Implement your logic to get the client's IP address
  }

  public static String getRandomNumber(int len) {
    Random rnd = new Random();
    String chars = "0123456789";
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++) {
      sb.append(chars.charAt(rnd.nextInt(chars.length())));
    }
    return sb.toString();
  }
}
