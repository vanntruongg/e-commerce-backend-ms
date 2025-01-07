package com.vantruong.inventory.controller;

import com.vantruong.inventory.common.CommonResponse;
import com.vantruong.inventory.constant.MessageConstant;
import com.vantruong.inventory.service.InternalInventoryService;
import com.vantruong.inventory.viewmodel.InventoryPostVm;
import com.vantruong.inventory.viewmodel.ProductCheckVm;
import com.vantruong.inventory.viewmodel.ProductListInventoryCheckVm;
import com.vantruong.inventory.viewmodel.ProductQuantityCheckVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.vantruong.inventory.constant.InternalApiEndpoint.*;

@RestController
@RequestMapping(INTERNAL + INVENTORY)
@RequiredArgsConstructor
public class InternalInventoryController {
  private final InternalInventoryService internalInventoryService;

  @PostMapping(CREATE_INVENTORY)
  public ResponseEntity<CommonResponse<Object>> createInventory(@RequestBody InventoryPostVm inventoryPost) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(internalInventoryService.createInventory(inventoryPost))
            .build());
  }

  @PostMapping(CHECK_PRODUCT_QUANTITY)
  public ResponseEntity<CommonResponse<Object>> checkProductQuantity(@RequestBody ProductCheckVm request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(internalInventoryService.checkProductQuantity(request))
            .build());
  }

  @PostMapping(CHECK_LIST_PRODUCT_QUANTITY)
  public ResponseEntity<CommonResponse<Object>> checkListProductQuantity(@RequestBody List<ProductQuantityCheckVm> request) {
    return ResponseEntity.ok().body(CommonResponse.builder()
            .isSuccess(true)
            .message(MessageConstant.SUCCESS)
            .data(internalInventoryService.checkListProductQuantityById(request))
            .build());
  }

  @PostMapping(GET_ALL_BY_PRODUCT_IDS)
  public ResponseEntity<CommonResponse<Object>> getAllInventoryByProductIds(@RequestBody ProductListInventoryCheckVm request) {
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
