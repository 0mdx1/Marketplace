package com.ncgroup.marketplaceserver.shopping.cart.service;

import com.ncgroup.marketplaceserver.exception.basic.NotFoundException;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemCreateDto;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemReadDto;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemUpdateDto;

import java.util.List;

public interface ShoppingCartItemService {

    public void put(ShoppingCartItemCreateDto shoppingCartItemDto);

    public void update(long id,ShoppingCartItemUpdateDto shoppingCartItemDto) throws NotFoundException;

    public void delete(long id) throws NotFoundException;

    public void deleteAll();

    public ShoppingCartItemReadDto get(long id) throws NotFoundException;

    public List<ShoppingCartItemReadDto> getAll();

    public void setAll(List<ShoppingCartItemCreateDto> shoppingCartItemCreateDtos);

}
