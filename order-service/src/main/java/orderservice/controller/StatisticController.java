package orderservice.controller;

import lombok.RequiredArgsConstructor;
import orderservice.common.CommonResponse;
import orderservice.constant.MessageConstant;
import orderservice.dto.OrderRequest;
import orderservice.service.OrderService;
import orderservice.service.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static orderservice.constant.ApiEndpoint.*;

@RestController
@RequestMapping(STATISTIC)
@RequiredArgsConstructor
public class StatisticController {
  private final StatisticService statisticService;

  @GetMapping(GET_TOTAL_ORDER)
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

  @GetMapping(REVENUE)
  public ResponseEntity<CommonResponse<Object>> getRevenue(@RequestParam("year") Integer year, @RequestParam(value = "month", required = false) Integer month) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(statisticService.getRevenue(year, month))
            .build());
  }

  @GetMapping(ORDER)
  public ResponseEntity<CommonResponse<Object>> statisticOrder(@RequestParam("year") Integer year, @RequestParam(value = "month", required = false) Integer month) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(statisticService.statisticOrder(year, month))
            .build());
  }

}
