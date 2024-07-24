package com.vantruong.order.controller;

import com.vantruong.order.common.CommonResponse;
import com.vantruong.order.constant.ApiEndpoint;
import com.vantruong.order.constant.MessageConstant;
import com.vantruong.order.service.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiEndpoint.STATISTIC)
@RequiredArgsConstructor
public class StatisticController {
  private final StatisticService statisticService;

  @GetMapping(ApiEndpoint.GET_TOTAL_ORDER)
  public ResponseEntity<CommonResponse<Object>> getTotalOrderCountByStatus() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(statisticService.getTotalOrderCountByStatus())
            .build());
  }

//  @GetMapping(COUNT_ORDER_BY_MONTH)
//  public ResponseEntity<CommonResponse<Object>> getCountOrderByMonth() {
//    return ResponseEntity.ok().body(CommonResponse.builder()
//            .isSuccess(true)
//            .message(MessageConstant.FIND_SUCCESS)
//            .data(statisticService.getCountOrderByMonth())
//            .build());
//  }

  @GetMapping(ApiEndpoint.REVENUE)
  public ResponseEntity<CommonResponse<Object>> getRevenue(@RequestParam("year") Integer year, @RequestParam(value = "month", required = false) Integer month) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(statisticService.getRevenue(year, month))
            .build());
  }

  @GetMapping(ApiEndpoint.ORDER)
  public ResponseEntity<CommonResponse<Object>> statisticOrder(@RequestParam("year") Integer year, @RequestParam(value = "month", required = false) Integer month) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(statisticService.statisticOrder(year, month))
            .build());
  }

}
