package com.ncgroup.marketplaceserver.shopping.cart.service;

import com.ncgroup.marketplaceserver.exception.basic.NotFoundException;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemCreateDto;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemReadDto;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemUpdateDto;

import java.util.List;

public interface ShoppingCartItemService {

    void put(ShoppingCartItemCreateDto shoppingCartItemDto);

    void update(long id, ShoppingCartItemUpdateDto shoppingCartItemDto) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    void deleteAll();

    ShoppingCartItemReadDto get(long id) throws NotFoundException;

    List<ShoppingCartItemReadDto> getAll();

    void setAll(List<ShoppingCartItemCreateDto> shoppingCartItemCreateDtos);

}
