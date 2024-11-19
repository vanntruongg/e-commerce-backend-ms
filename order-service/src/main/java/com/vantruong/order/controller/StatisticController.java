package com.vantruong.order.controller;

import com.vantruong.order.common.CommonResponse;
import com.vantruong.order.constant.ApiEndpoint;
import com.vantruong.order.constant.MessageConstant;
import com.vantruong.order.service.StatisticService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.vantruong.order.constant.ApiEndpoint.REVENUE;

@RestController
@RequestMapping(ApiEndpoint.ORDER + ApiEndpoint.STATISTIC)
public class StatisticController {
  private final StatisticService statisticService;

  public StatisticController(StatisticService statisticService) {
    this.statisticService = statisticService;
  }

  @GetMapping(ApiEndpoint.GET_TOTAL_ORDER)
  public ResponseEntity<CommonResponse<Object>> getTotalOrderCountByStatus() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(statisticService.countOrder())
            .build());
  }

//  @GetMapping(ApiEndpoint.REVENUE)
//  public ResponseEntity<CommonResponse<Object>> getRevenue(
//          @RequestParam("year") Integer year,
//          @RequestParam(value = "month", required = false) Integer month
//  ) {
//    return ResponseEntity.ok().body(CommonResponse.builder()
//            .isSuccess(true)
//            .message(MessageConstant.FIND_SUCCESS)
//            .data(statisticService.getRevenue(year, month))
//            .build());
//  }

  @GetMapping(REVENUE)
  public ResponseEntity<CommonResponse<Object>> getOrderRevenue(
          @RequestParam("year") Integer year,
          @RequestParam(value = "month", required = false) Integer month
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(statisticService.getOrderRevenue(year, month))
            .build());
  }

  @GetMapping(ApiEndpoint.ORDER)
  public ResponseEntity<CommonResponse<Object>> statisticOrder(
          @RequestParam("year") Integer year,
          @RequestParam(value = "month", required = false) Integer month
  ) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(statisticService.statisticOrder(year, month))
            .build());
  }

  @GetMapping("/product/total-sold")
  public ResponseEntity<CommonResponse<Object>> getTotalQuantitySoldPerProduct() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(statisticService.getTotalQuantitySoldPerProduct())
            .build());
  }

  @GetMapping("/monthly-order-stats")
  public ResponseEntity<CommonResponse<Object>> getTotalOrderPerMonth() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(statisticService.getTotalOrdersPerMonth())
            .build());
  }

  @GetMapping("/monthly-revenue-stats")
  public ResponseEntity<CommonResponse<Object>> getTotalRevenuePerMonth() {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(statisticService.getTotalRevenuePerMonth())
            .build());
  }

  @GetMapping("/export-csv-order-stats")
  public ResponseEntity<byte[]> exportCsvOrder() {
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=monthly-stats.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(statisticService.exportCsvOrder());

//    byte[] csvData = statisticService.exportCsv();
//
//    HttpHeaders headers = new HttpHeaders();
//    headers.setContentType(MediaType.TEXT_PLAIN);
//    headers.setContentDispositionFormData("attachment", "orders.csv");
//
//    return new ResponseEntity<>(csvData, headers, HttpStatus.OK);
  }

  @GetMapping("/export-csv-revenue-stats")
  public ResponseEntity<byte[]> exportCsvRevenue() {
    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=monthly-stats.csv")
            .contentType(MediaType.parseMediaType("text/csv"))
            .body(statisticService.exportCsvRevenue());
  }
}
