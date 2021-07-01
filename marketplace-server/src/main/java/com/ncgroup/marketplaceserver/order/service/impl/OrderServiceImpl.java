package com.ncgroup.marketplaceserver.order.service.impl;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ncgroup.marketplaceserver.exception.basic.NotFoundException;
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
		String email = getTokenEmail(token); //courier email
		long courierId = userService.findUserByEmail(email).getId();
		List<OrderReadDto> ordersDto = orderRepo.getOrders(courierId, page-1)
				.stream().map(OrderReadDto::convertToDto).collect(Collectors.toList());
		
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
		return order == null ? null : OrderReadDto.convertToDto(order);
	}

	@Override
	@Transactional
	public OrderReadDto addOrder(OrderPostDto orderDto, String token)  {
		log.info("Add time: "+orderDto.getDeliveryTime()); 
		Order order = OrderPostDto.toOrder(orderDto);
		if(token != null) {
			String email = getTokenEmail(token); //user email
			order.getUser().setId(userService.findUserByEmail(email).getId());
		} else {
			long userId = userService.addUserWithoutCredentials(
					order.getUser().getName(), order.getUser().getSurname(), order.getUser().getPhone()).getId();
			order.getUser().setId(userId);
		}
		
		long courierId = -1;
		try {
			courierId = orderRepo.getFreeCourierId(orderDto.getDeliveryTime());
		} catch (Exception e) {
			throw new NoCouriersException("Sorry, there are no available couriers for that time");
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
		for (OrderItem item : order.getItems()) {
			try {
				double oldQunatity = goodService.find(item.getGood().getId()).getQuantity();
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
	public List<OffsetDateTime> getFreeSlots() {
		if(orderRepo.getAvailableCouriersNum() < 1) {
			throw new NoCouriersException("Sorry, there are no available couriers");
		}
		List<OffsetDateTime> freeSlots = new LinkedList<>();
		List<OffsetDateTime> busySlots = orderRepo.findBusySlots();
		log.info(busySlots.toString());
		OffsetDateTime endDay = OffsetDateTime.now();
		while (endDay.isBefore(OffsetDateTime.now().plusWeeks(2).withMinute(0).withSecond(0).withNano(0))) {
			//endDay = endDay.withHour(endDay.getHour()+1).withMinute(0);
			while(endDay.getHour() <= 18) {
				log.info("End day: " + endDay.toString());
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
		if (order.getStatus().equals(OrderStatus.SUBMITTED)) {
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
		String email = getTokenEmail(token); 
		return orderRepo.findUserForOrder(email);
	}
	
	@Override
	@Transactional
	public List<OrderReadDto> getUserOrders(String token) {
		if(token == null) {
			return null;
		}
		return orderRepo.getUserIncomingOrders(getTokenEmail(token))
				.stream().map(OrderReadDto::convertToDto).collect(Collectors.toList());
	}
	
	@Override
	@Transactional
	public List<OrderReadDto> getUserHistory(String token){
		if(token == null) {
			return null;
		}
		return orderRepo.getUserHistoryOrders(getTokenEmail(token))
				.stream().map(OrderReadDto::convertToDto).collect(Collectors.toList());
	}
	
	@Override
	public UserDisplayInfoDto getCourierInfoForOrder(long id) {
		return orderRepo.findCourierForOrder(id);
	}
	
	private float calculateSum(long goodId, int quantity) {
		try {
			Good good = goodService.find(goodId);
			return ((float) (good.getPrice() - good.getPrice() * good.getDiscount()/100)) * quantity;
		} catch (NotFoundException e) {
			return 0;
		}
	}
	
	private String getTokenEmail(String token) {
		return token == null ? null : jwtProvider.getSubject(token.split(" ")[1]);
	}
	

}
