package com.vantruong.inventory.controller;

import com.vantruong.inventory.common.CommonResponse;
import com.vantruong.inventory.constant.MessageConstant;
import com.vantruong.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.vantruong.inventory.constant.ApiEndpoint.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(INVENTORY)
public class InventoryController {
  private final InventoryService inventoryService;


  @GetMapping(GET + ID_PARAM)
  public ResponseEntity<CommonResponse<Object>> getByProductId(@PathVariable("id") Integer productId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(inventoryService.findByProductId(productId))
            .build());
  }

  @GetMapping(GET + SIZE + ID)
  public ResponseEntity<CommonResponse<Object>> getByProductIdAndSize(@RequestParam("size") String size, @RequestParam("id") Integer id) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.FIND_SUCCESS)
            .data(inventoryService.findByProductIdAndSize(size, id))
            .build());
  }
}
