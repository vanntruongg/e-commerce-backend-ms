package com.vantruong.inventory.controller;

import com.vantruong.common.dto.inventory.InventoryPost;
import com.vantruong.common.dto.request.ProductInventoryRequest;
import com.vantruong.common.dto.request.ProductQuantityRequest;
import com.vantruong.inventory.common.CommonResponse;
import com.vantruong.inventory.constant.MessageConstant;
import com.vantruong.inventory.service.InternalInventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.vantruong.common.constant.InternalApiEndpoint.*;
import static com.vantruong.common.constant.InternalApiEndpoint.INTERNAL;
import static com.vantruong.common.constant.InternalApiEndpoint.INVENTORY;

@RestController
@RequestMapping(INTERNAL + INVENTORY)
@RequiredArgsConstructor
public class InternalInventoryController {
  private final InternalInventoryService internalInventoryService;

  @PostMapping(CREATE_INVENTORY)
  public ResponseEntity<CommonResponse<Object>> createInventory(@RequestBody InventoryPost inventoryPost) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(internalInventoryService.createInventory(inventoryPost))
            .build());
  }

  @PostMapping(CHECK + PRODUCT + QUANTITY)
  public ResponseEntity<CommonResponse<Object>> checkProductQuantity(@RequestBody ProductQuantityRequest request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(internalInventoryService.checkProductQuantity(request))
            .build());
  }

  @PostMapping(CHECK_LIST_PRODUCT_QUANTITY)
  public ResponseEntity<CommonResponse<Object>> checkListProductQuantity(@RequestBody List<ProductQuantityRequest> request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(internalInventoryService.checkListProductQuantityById(request))
            .build());
  }

  @PostMapping(GET_ALL_BY_PRODUCT_IDS)
  public ResponseEntity<CommonResponse<Object>> getAllInventoryByProductIds(@RequestBody ProductInventoryRequest request) {
//    throw new NotFoundException(Constant.ErrorCode.NOT_FOUND, MessageConstant.NOT_FOUND);
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(internalInventoryService.getAllInventoryByProductIds(request))
            .build());
  }

  @GetMapping(GET_BY_PRODUCT_ID)
  public ResponseEntity<CommonResponse<Object>> getInventoryByProductId(@PathVariable("id") Long productId) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(internalInventoryService.getInventoryByProductId(productId))
            .build());
  }
}
