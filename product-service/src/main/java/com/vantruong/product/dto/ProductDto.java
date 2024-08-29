package com.vantruong.product.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductDto {
  private int id;
  private String name;
  private double price;
  private String material;
  private String style;
  private List<String> imageUrl;
  private int categoryId;
  private int stock;
}
