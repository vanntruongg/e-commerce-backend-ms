package com.vantruong.order.service;

import com.vantruong.common.dto.product.ProductSoldResponse;
import com.vantruong.order.dto.OrderRevenueDto;
import com.vantruong.order.dto.OrderStatsResponse;
import com.vantruong.order.repository.OrderItemRepository;
import com.vantruong.order.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticService {
  OrderRepository orderRepository;
  static int MONTH_IN_YEAR = 12;
  private final OrderItemRepository orderItemRepository;

//  public Map<String, Long> getTotalOrderCountByStatus() {
//    List<Object[]> results = orderRepository.findOrderCountByStatus();
//    Map<String, Long> orderCountByStatus = new HashMap<>();
//    for (Object[] result : results) {
//      orderCountByStatus.put(result[0].toString(), (Long) result[1]);
//    }
//    return orderCountByStatus;
//  }

//  public Map<Integer, Integer> getCountOrderByMonth() {
//    List<Object[]> results = orderRepository.countOrderByMonth();
//    Map<Integer, Integer> orderCountByMonth = new HashMap<>();
//
//    // initial the order for each month to 0
//    for (int i = 1; i <= MONTH_IN_YEAR; i++) {
//      orderCountByMonth.put(i, 0);
//    }
//
//    // iterate through the results update the order count by month
//    for (Object[] result : results) {
//      int month = (int) result[0];
//      int count = ((Number) result[1]).intValue();
//
//      // update the order count for the corresponding month in the map
//      orderCountByMonth.put(month, count);
//    }
//    return orderCountByMonth;
//  }

  private int getMonthsOrDays(Integer year, Integer month) {
    return Objects.isNull(month) ? MONTH_IN_YEAR : LocalDate.of(year, month, 1).lengthOfMonth();
  }

  public long countOrder() {
    return orderRepository.count();
  }

  public List<OrderRevenueDto> getOrderRevenue(Integer year, Integer month) {
    List<OrderRevenueDto> orderRevenueListDto = new ArrayList<>();

    for (int i = 1; i <= getMonthsOrDays(year, month); i++) {
      OrderRevenueDto orderRevenueDto = OrderRevenueDto.builder()
              .totalOrder(0)
              .totalRevenue(0.0)
              .periodValue(i)
              .build();
      orderRevenueListDto.add(orderRevenueDto);
    }

    Set<Object[]> results = !Objects.isNull(month)
            ? orderRepository.getTotalRevenueByMonthInYear(year, month)
            : orderRepository.getTotalRevenueByYear(year);


    if (!results.isEmpty()) {
      for (Object[] result : results) {
        orderRevenueListDto.stream()
                .filter(orderRevenue -> orderRevenue.getPeriodValue() == (Integer) result[0])
                .findFirst()
                .ifPresent(orderRevenue -> {
                  orderRevenue.setTotalOrder((Long) result[1]);
                  orderRevenue.setTotalRevenue((Double) result[2]);
                });
      }
    }

    return orderRevenueListDto;
  }

  public Map<Integer, Double> getRevenue(Integer year, Integer month) {
    Map<Integer, Double> revenue = new LinkedHashMap<>();

    initializeRevenueMap(revenue, year, month, 0.0);

    Set<Object[]> results = !Objects.isNull(month)
            ? orderRepository.getTotalRevenueByMonthInYear(year, month)
            : orderRepository.getTotalRevenueByYear(year);

    if (!results.isEmpty()) {
      for (Object[] result : results) {
        revenue.put((Integer) result[0], (Double) result[1]);
      }
    }
    return revenue;
  }

  public Map<Integer, Long> statisticOrder(Integer year, Integer month) {
    Map<Integer, Long> totalOrder = new LinkedHashMap<>();

    initializeRevenueMap(totalOrder, year, month, 0L);

    Set<Object[]> results = !Objects.isNull(month)
            ? orderRepository.getTotalOrderByMonthInYear(year, month)
            : orderRepository.getTotalOrderByYear(year);

    if (!results.isEmpty()) {
      for (Object[] result : results) {
        totalOrder.put((Integer) result[0], (Long) result[1]);
      }
    }
    return totalOrder;
  }

  // Method to initialize revenue map with default values
  private <T extends Number> void initializeRevenueMap(Map<Integer, T> revenue, Integer year, Integer month, T defaultValue) {
    int numMonths = (Objects.isNull(month)) ? MONTH_IN_YEAR : LocalDate.of(year, month, 1).lengthOfMonth();
    for (int i = 1; i <= numMonths; i++) {
      revenue.put(i, defaultValue);
    }
  }

  public List<OrderStatsResponse> getTotalOrdersPerMonth() {
    LocalDate now = LocalDate.now();
    int currentYear = now.getYear();

// get current month and subtract 1 to the purpose of predicting the upcoming month
    int currentMonth = now.getMonthValue() - 1;

    Set<Object[]> results = orderRepository.getTotalOrderByYear(currentYear);

    // map results to map with key is month and value is totalOrder
    Map<Integer, Integer> ordersPerMonth = results.stream()
            .collect(Collectors.toMap(
                    result -> (int) result[0],
                    result -> ((Number) result[1]).intValue()
            ));

    return IntStream.rangeClosed(1, currentMonth)
            .mapToObj(month ->
                    new OrderStatsResponse(
                            month,
                            ordersPerMonth.getOrDefault(month, 0))
            )
            .toList();
  }

  public List<ProductSoldResponse> getTotalQuantitySoldPerProduct() {
    List<Object[]> results = orderItemRepository.getTotalQuantityPerProduct();

    return results.stream()
            .map(result ->
                    new ProductSoldResponse(
                            (Long) result[0],
                            ((Number) result[1]).intValue()
                    )
            )
            .toList();
  }

}
