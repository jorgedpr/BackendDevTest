package com.example.backendDevTest.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.backendDevTest.dto.ProductDetail;
import com.example.backendDevTest.service.BackendDevTestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BackendDevTestServiceImpl implements BackendDevTestService {

	@Value("${mocksUri}")
	private String mocksUri;
	
	@Override
	public List<ProductDetail> getProductDetailOfTheSimilarProducts(String productId) {
		// Return a list of similar products to a given one ordered by similarity
		
		List<ProductDetail> productDetails = new ArrayList<ProductDetail>();

		// 1. We use the first endpoint that provides the product Ids similar for a given one
		List<String> similarProductIds = getSimilarProducts(productId);
		
		// 2. Once we get the list of similar products, we get the product details of each similar product
		for(String similarProductId: similarProductIds) {
			productDetails.add(getProductDetail(similarProductId));
		}
		
		//3. Return the list
		return productDetails;
	}

	private ProductDetail getProductDetail(String similarProductId) {
		//uri -> http://localhost:3001/product/{productId}/
		String uri = mocksUri + "/product/" + similarProductId;
		ProductDetail productDetail = new ProductDetail();

		//Request to the endpoint
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> input = new HttpEntity<String>(headers);

		RestTemplate template = new RestTemplate();
		ResponseEntity<byte[]> result = template.exchange(uri, HttpMethod.GET, input, byte[].class);
		String responseString = new String(result.getBody(), StandardCharsets.UTF_8);

		// Convert JSON to ProductDetail object
	    ObjectMapper objectMapper = new ObjectMapper();
	    try {
	        productDetail = objectMapper.readValue(responseString, ProductDetail.class);
	    } catch (JsonProcessingException e) {
	        // Handle exception if JSON parsing fails
	        e.printStackTrace();
	    }
		
		return productDetail;
	}

	private List<String> getSimilarProducts(String productId) {
		
		//uri -> http://localhost:3001/product/{productId}/similarids
		String uri = mocksUri + "/product/" + productId + "/similarids";

		//Request to the endpoint
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> input = new HttpEntity<String>(headers);

		RestTemplate template = new RestTemplate();
		ResponseEntity<byte[]> result = template.exchange(uri, HttpMethod.GET, input, byte[].class);
		String responseString = new String(result.getBody(), StandardCharsets.UTF_8);

		// Parse the response array
		List<String> response = Arrays.asList(responseString.substring(1, responseString.length() - 1).split(","));
		
		return response;
	}

}
