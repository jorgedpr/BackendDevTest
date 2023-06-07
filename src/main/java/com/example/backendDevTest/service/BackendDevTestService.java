package com.example.backendDevTest.service;

import java.util.List;

import com.example.backendDevTest.dto.ProductDetail;

public interface BackendDevTestService {

	List<ProductDetail> getProductDetailOfTheSimilarProducts(String productId);
}
