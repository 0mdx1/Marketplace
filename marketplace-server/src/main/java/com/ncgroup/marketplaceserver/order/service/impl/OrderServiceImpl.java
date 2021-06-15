package com.ncgroup.marketplaceserver.order.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ncgroup.marketplaceserver.exception.domain.NotFoundException;
import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.goods.service.GoodsService;
import com.ncgroup.marketplaceserver.model.Courier;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.model.dto.UserDisplayInfoDto;
import com.ncgroup.marketplaceserver.order.exception.NoCouriersException;
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

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
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
	@Transactional
	public Map<String, Object> getCourierOrders(String token, int page) {
		token = token.split(" ")[1];
		String email = jwtProvider.getSubject(token); //courier email
		long courierId = userService.findUserByEmail(email).getId();
		List<Order> orders = orderRepo.getOrders(courierId, page-1);
		List<OrderReadDto> ordersDto = new LinkedList<>();
		for(Order order : orders) {
			ordersDto.add(OrderReadDto.convertToDto(order));
		}
		
		int totalPages = orderRepo.getTotalPages(courierId);
		
		Map<String, Object> result = new HashMap<>();

        result.put("orders", ordersDto);
        result.put("page", page);
        result.put("totalPages", totalPages % 10 == 0 ? totalPages / 10 : totalPages / 10 + 1);
		return result;
	}

	@Override
	public OrderReadDto getOrder(long id) {
		Order order = orderRepo.getOrder(id);
		if(order == null) return null;
		return OrderReadDto.convertToDto(order);
	}

	@Override
	@Transactional
	public OrderReadDto addOrder(OrderPostDto orderDto, String token)  {
		Order order = OrderPostDto.toOrder(orderDto);
		if(token != null) {
			token = token.split(" ")[1];
			order.getUser().setId(userService.findUserByEmail(jwtProvider.getSubject(token)).getId());
		} else {
			long userId = userService.addUserWithoutCredentials(
					order.getUser().getName(), order.getUser().getSurname(), order.getUser().getPhone()).getId();
			order.getUser().setId(userId);
		}
		
		long courierId = -1;
		try {
			courierId = orderRepo.getFreeCourierId(orderDto.getDeliveryTime());
		} catch (Exception e) {
			throw new NoCouriersException("Sorry, there are no free couriers for that time");
		}
		
		order.setCourier(
				Courier
				.builder()
				.user(
						User
						.builder()
						.id(courierId)
						.build()
						)
				.build()
				);
		
		order = orderRepo.saveOrderDetails(order);
		for(OrderItem item : order.getItems()) {
			try {
				int oldQunatity = goodService.findById(item.getGood().getId()).getQuantity();
				goodService.updateQuantity(item.getGood().getId(), oldQunatity-item.getQuantity());
			} catch (NotFoundException e) {
				log.warn(e.getMessage());
			}
			item.setPrice(calculateSum(item.getGood().getId(), item.getQuantity()));
			orderRepo.saveOrderGood(item, order.getId());
		}
		return OrderReadDto.convertToDto(order);
	}
	
	@Override
	public List<LocalDateTime> getFreeSlots() {
		List<LocalDateTime> freeSlots = new LinkedList<LocalDateTime>();
		List<LocalDateTime> busySlots = orderRepo.findFreeSlots();
		LocalDateTime endDay = LocalDateTime.now();
		while(endDay.isBefore(LocalDateTime.now().plusWeeks(2).withMinute(0).withSecond(0).withNano(0))) {
			//endDay = endDay.withHour(endDay.getHour()+1).withMinute(0);
			while(endDay.getHour() <= 18) {
				endDay = endDay.withHour(endDay.getHour()+1).withMinute(0).withSecond(0).withNano(0);
				if(!busySlots.contains(endDay)) {
					freeSlots.add(endDay);
				}
			}
			
			endDay = endDay.plusDays(1);
			endDay = endDay.withHour(9).withMinute(0).withSecond(0).withNano(0);		}
		return freeSlots;
	}
	  
	@Override
	@Transactional
	public OrderStatus modifyStatus(long id) {
		Order order = orderRepo.getOrder(id);
		if(order.getStatus().equals(OrderStatus.SUBMITTED)) {
			orderRepo.modifyStatus(id, OrderStatus.IN_DELIVERY);
			return OrderStatus.IN_DELIVERY;
		} else if (order.getStatus().equals(OrderStatus.IN_DELIVERY)) {
			orderRepo.modifyStatus(id, OrderStatus.DELIVERED);
			return OrderStatus.DELIVERED;
		}
		return OrderStatus.DELIVERED;
	}
	
	@Override
	public UserDisplayInfoDto getUserInfoForOrder(String token) {
		if(token == null) return null;
		token = token.split(" ")[1];
		String email = jwtProvider.getSubject(token); 
		return orderRepo.findUserForOrder(email);
	}
	
	private float calculateSum(long goodId, int quantity) {
		
		try {
			Good good = goodService.findById(goodId);
			return ((float) (good.getPrice() - good.getPrice() * good.getDiscount()/100)) * quantity;
		} catch (NotFoundException e) {
			return 0;
		}
		

	}

	

}
