package com.vantruong.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public class ServiceUrls {
  private static final String INTERNAL = "/internal";
  private static final String IDENTITY_SERVICE_URL = "http://localhost:9001/identity";
  private static final String USER_SERVICE_URL = "http://localhost:9002/user";
  private static final String PRODUCT_SERVICE_URL = "http://localhost:9003/product";
  private static final String INVENTORY_SERVICE_URL = "http://localhost:9004/inventory";
  private static final String CART_SERVICE_URL = "http://localhost:9005/cart";
  private static final String ORDER_SERVICE_URL = "http://localhost:9006/order";
  private static final String PAYMENT_SERVICE_URL = "http://localhost:9007/payment";
  private static final String RATING_SERVICE_URL = "http://localhost:9008/rating";
  private static final String NOTIFICATION_SERVICE_URL = "http://localhost:9009/notification";
  private static final String AI_SERVICE_URL = "http://localhost:9010/ai";
//  private static final String SAGA_ORCHESTRATION_SERVICE_URL = "http://localhost:9011/saga-orchestration";
}
