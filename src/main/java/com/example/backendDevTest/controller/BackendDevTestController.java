package com.example.backendDevTest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backendDevTest.service.BackendDevTestService;


@RestController
@RequestMapping()
public class BackendDevTestController {

	@Autowired
	private BackendDevTestService backendDevTestService;
	
	@GetMapping("/product/{productId}/similar") //Path of the agreed endpoint
	public ResponseEntity<?> getProductDetailOfTheSimilarProducts(@PathVariable String productId) throws Exception{
		
		try {
			//If the operation success returns a 200 with the detail of the similar products
			return ResponseEntity.ok(backendDevTestService.getProductDetailOfTheSimilarProducts(productId));
		}
		catch (Exception e) {
			//If not, returns a 404 (Product Not found)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
		}
	}
	
}
