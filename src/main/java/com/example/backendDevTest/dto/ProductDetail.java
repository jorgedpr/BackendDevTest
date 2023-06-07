package com.example.backendDevTest.dto;

import lombok.Data;

@Data
public class ProductDetail {

	private String id;
	private String name;
	private double price;
	private boolean availability;
}
