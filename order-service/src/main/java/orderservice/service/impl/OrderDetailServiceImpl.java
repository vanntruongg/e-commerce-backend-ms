package orderservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import orderservice.entity.Order;
import orderservice.entity.OrderDetail;
import orderservice.dto.OrderDetailDto;
import orderservice.dto.internal.RemoveItemsCartRequest;
import orderservice.repository.OrderDetailRepository;
import orderservice.repository.client.CartClient;
import orderservice.repository.client.ProductClient;
import orderservice.service.OrderDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailServiceImpl implements OrderDetailService {
  OrderDetailRepository orderDetailRepository;
  ProductClient productClient;
  CartClient cartClient;

  @Override
  @Transactional
  public List<OrderDetail> createOrderDetails(Order order, List<OrderDetailDto> orderDetailDTOs) {
    List<OrderDetail> orderDetails = new ArrayList<>();
    Map<Integer, Integer> stockUpdate = new HashMap<>();

    orderDetailDTOs.forEach(orderDetailDto -> {
      OrderDetail orderDetail = mapOrderDetailDTOToOrderDetail(orderDetailDto);
      orderDetail.setOrder(order);
      orderDetails.add(orderDetail);
      stockUpdate.put(orderDetailDto.getProductId(), orderDetailDto.getQuantity());
//    call the product service to update product quantity
    });

    orderDetailRepository.saveAll(orderDetails);

    productClient.updateProductQuantity(stockUpdate);

//  call the cart service to remove items from the cart
    List<Integer> productIds = orderDetailDTOs.stream().map(OrderDetailDto::getProductId).toList();
    RemoveItemsCartRequest removeItemsCartRequest = RemoveItemsCartRequest.builder()
            .email(order.getEmail())
            .productIds(productIds)
            .build();
    cartClient.removeItemsFromCart(removeItemsCartRequest);

    return orderDetails;
  }

  @Override
  public List<OrderDetail> getAllOrderDetailByOrder(Order order) {
    return orderDetailRepository.findAllByOrder(order);
  }

  private OrderDetail mapOrderDetailDTOToOrderDetail(OrderDetailDto orderDetailDto) {
    return OrderDetail.builder()
            .productId(orderDetailDto.getProductId())
            .productName(orderDetailDto.getProductName())
            .quantity(orderDetailDto.getQuantity())
            .productPrice(orderDetailDto.getProductPrice())
            .productImage(orderDetailDto.getProductImage())
            .build();
  }
}
