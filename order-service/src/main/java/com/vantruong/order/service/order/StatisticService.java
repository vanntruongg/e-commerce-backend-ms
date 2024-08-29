package com.vantruong.order.service.order;

import java.util.Map;

public interface StatisticService {

  Map<String, Long> getTotalOrderCountByStatus();
  Map<Integer, Integer> getCountOrderByMonth();

  Map<Integer, Double> getRevenue(Integer year, Integer month);

  Map<Integer, Long> statisticOrder(Integer year, Integer month);
}
