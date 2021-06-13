package com.ncgroup.marketplaceserver.order.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.goods.service.GoodsService;
import com.ncgroup.marketplaceserver.order.model.Order;
import com.ncgroup.marketplaceserver.order.model.OrderItem;
import com.ncgroup.marketplaceserver.order.model.OrderStatus;
import com.ncgroup.marketplaceserver.order.model.dto.OrderPostDto;
import com.ncgroup.marketplaceserver.order.model.dto.OrderReadDto;
import com.ncgroup.marketplaceserver.order.repository.OrderRepository;
import com.ncgroup.marketplaceserver.order.service.OrderService;
import com.ncgroup.marketplaceserver.repository.UserRepository;
import com.ncgroup.marketplaceserver.security.util.JwtProvider;
import com.ncgroup.marketplaceserver.service.UserService;
import com.ncgroup.marketplaceserver.shopping.cart.exceptions.NotFoundException;

@Service
public class OrderServiceImpl implements OrderService{
	
	private OrderRepository orderRepo;
	private UserService userService;
	private GoodsService goodService;
	private JwtProvider jwtProvider;
	
	@Autowired
	public OrderServiceImpl(
			OrderRepository orderRepo, 
			UserService userService,
			GoodsService goodService,
			JwtProvider jwtProvider) {
		this.orderRepo = orderRepo;
		this.userService = userService;
		this.goodService = goodService;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public List<OrderReadDto> getCourierOrders(String token, int page) {
		token = token.split(" ")[1];
		String email = jwtProvider.getSubject(token); //courier email
		List<Order> orders = orderRepo.getOrders(
				userService.findUserByEmail(email).getId(), 
				page-1);
		List<OrderReadDto> ordersDto = new LinkedList<>();
		for(Order order : orders) {
			ordersDto.add(OrderReadDto.convertToDto(order));
		}
		return ordersDto;
	}

	@Override
	public OrderReadDto getOrder(long id) {
		Order order = orderRepo.getOrder(id);
		if(order == null) return null;
		return OrderReadDto.convertToDto(orderRepo.getOrder(id));
	}

	@Override
	public OrderPostDto addOrder(OrderPostDto orderDto) throws NotFoundException {
		/*Order order = OrderPostDto.toOrder(orderDto);
		if(order.getUser().getEmail() != null) {
			order.getUser().setId(userService.findUserByEmail(order.getUser().getEmail()).getId());
		} else {
			long userId = userService.addUserWithoutCredentials(
					order.getUser().getName(), order.getUser().getSurname(), order.getUser().getPhone()).getId();
			order.getUser().setId(userId);
		}
		order.setCourier(null);
		
		order = orderRepo.saveOrderDetails(order);
		for(OrderItem item : order.getItems()) {
			item.setSum(calculateSum(item.getGood().getId(), item.getQuantity()));
			orderRepo.saveOrderGood(item, order.getId());
		}
		return orderDto;*/
		return null;
	}
	
	@Override
	public OrderReadDto modifyStatus(long id) {
		Order order = orderRepo.getOrder(id);
		if(order.getStatus().equals(OrderStatus.SUBMITTED)) {
			orderRepo.modifyStatus(id, OrderStatus.IN_DELIVERY);
			order.setStatus(OrderStatus.IN_DELIVERY);
		} else if (order.getStatus().equals(OrderStatus.IN_DELIVERY)) {
			orderRepo.modifyStatus(id, OrderStatus.DELIVERED);
			order.setStatus(OrderStatus.DELIVERED);
		}
		return OrderReadDto.convertToDto(orderRepo.getOrder(id));
	}
	
	private float calculateSum(long goodId, int quantity) throws NotFoundException {
		Good good = goodService.findById(goodId);
		return ((float) (good.getPrice()-good.getPrice()*good.getDiscount()))*quantity;
		
	}

	

}
