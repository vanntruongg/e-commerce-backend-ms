package com.vantruong.order.service;

import com.vantruong.common.dto.product.ProductSoldResponse;
import com.vantruong.order.dto.HasMonth;
import com.vantruong.order.dto.OrderRevenueDto;
import com.vantruong.order.dto.OrderStatsResponse;
import com.vantruong.order.dto.RevenueStatsResponse;
import com.vantruong.order.repository.OrderItemRepository;
import com.vantruong.order.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticService {
  OrderRepository orderRepository;
  static int MONTH_IN_YEAR = 12;
  private final OrderItemRepository orderItemRepository;

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
            : orderRepository.getTotalOrderAndTotalRevenueByYear(year);


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


  public List<RevenueStatsResponse> getTotalRevenuePerMonth() {
    LocalDate now = LocalDate.now();
    int currentYear = now.getYear();
    int currentMonth = now.getMonthValue();
    return orderRepository.getToTalRevenuePerMonth(currentYear, currentMonth);

//    return createStatsResponse(results, RevenueStatsResponse::new);
  }

  public List<OrderStatsResponse> getTotalOrdersPerMonth() {
    LocalDate now = LocalDate.now();
    int currentYear = now.getYear();
    int currentMonth = now.getMonthValue();
    return orderRepository.getTotalOrderByYear(currentYear, currentMonth);
  }

//  private <T> List<T> createStatsResponse(Collection<Object[]> results, BiFunction<String, Number, T> responseConstructor) {
//    Map<String, Number> statsMap = results.stream()
//            .collect(Collectors.toMap(
//                    result -> (String) result[0],
//                    result -> (Number) result[1]
//            ));
//
//    List<T> responseList = new ArrayList<>();
//    statsMap.forEach((key, value) -> responseList.add(responseConstructor.apply(key, value)));
//    sortStatsByDate(responseList);
//
//    return responseList;
//  }
//
//  private <T> void sortStatsByDate(List<T> statsResponseList) {
//    statsResponseList.sort(Comparator.comparing(response -> {
//      String month = ((HasMonth) response).getMonth();
//      String[] parts = month.split("-");
//      return LocalDate.of(Integer.parseInt(parts[1]), Integer.parseInt(parts[0]), 1);
//    }));
//  }

  public byte[] exportCsvOrder() {
    List<OrderStatsResponse> orderStatsResponseList = getTotalOrdersPerMonth();
    String columnName = "Orders";
    return createCsvFile(orderStatsResponseList, columnName);
  }

  public byte[] exportCsvRevenue() {
    List<RevenueStatsResponse> orderStatsResponseList = getTotalRevenuePerMonth();
    String columnName = "Revenue";
    return createCsvFile(orderStatsResponseList, columnName);
  }


  public <T extends HasMonth> byte[] createCsvFile(List<T> statsResponseList, String columnName) {
    StringBuilder csvBuilder = new StringBuilder();

    // Thêm dòng tiêu đề
    csvBuilder.append("Month," + columnName + "\n");

    // Duyệt qua dữ liệu và thêm từng dòng vào CSV
    for (T statsResponse : statsResponseList) {
      csvBuilder.append(statsResponse.getMonth())
              .append(",")
              .append(statsResponse.getValue())
              .append("\n");
    }
    // Chuyển nội dung CSV sang mảng byte để trả về
    return csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
  }


}
