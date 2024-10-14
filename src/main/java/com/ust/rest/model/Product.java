package com.ust.rest.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
	private long productId;
	private String brand;
	private String description;
	private int quantity;
	private int price;
}
