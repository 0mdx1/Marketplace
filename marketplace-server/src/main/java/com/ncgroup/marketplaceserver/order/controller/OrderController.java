package com.ncgroup.marketplaceserver.order.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ncgroup.marketplaceserver.order.model.OrderReadDto;

@RequestMapping("/api/order")
@RestController
public class OrderController {
	
	/*@GetMapping("/{id}")
	public ResponseEntity<List<OrderReadDto>> getOrdersForCourier(@PathVariable("id") long courierId) {
		
	}
	
	public ResponseEntity<?> */

}
