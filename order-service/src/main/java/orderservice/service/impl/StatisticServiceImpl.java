package orderservice.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import orderservice.repository.OrderRepository;
import orderservice.service.StatisticService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticServiceImpl implements StatisticService {
  OrderRepository orderRepository;
  static int MONTH_IN_YEAR = 12;


  @Override
  public Map<String, Long> getTotalOrderCountByStatus() {
    List<Object[]> results = orderRepository.findOrderCountByStatus();
    Map<String, Long> orderCountByStatus = new HashMap<>();
    for (Object[] result : results) {
      orderCountByStatus.put(result[0].toString(), (Long) result[1]);
    }
    return orderCountByStatus;
  }

  @Override
  public Map<Integer, Integer> getCountOrderByMonth() {
    List<Object[]> results = orderRepository.countOrderByMonth();
    Map<Integer, Integer> orderCountByMonth = new HashMap<>();

    // initial the order for each month to 0
    for (int i = 1; i <= MONTH_IN_YEAR; i++) {
      orderCountByMonth.put(i, 0);
    }

    // iterate through the results update the order count by month
    for (Object[] result : results) {
      int month = (int) result[0];
      int count = ((Number) result[1]).intValue();

      // update the order count for the corresponding month in the map
      orderCountByMonth.put(month, count);
    }
    return orderCountByMonth;
  }

  @Override
  public Map<Integer, Double> getRevenue(Integer year, Integer month) {
    Map<Integer, Double> revenue = new LinkedHashMap<>();

    initializeRevenueMap(revenue, year, month, 0.0);

    Set<Object[]> results = !Objects.isNull(month)
            ? orderRepository.getTotalPriceByMonthInYear(year, month)
            : orderRepository.getTotalPriceByYear(year);

    if (!results.isEmpty()) {
      for (Object[] result : results) {
        revenue.put((Integer) result[0], (Double) result[1]);
      }
    }
    return revenue;
  }

  @Override
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
}
