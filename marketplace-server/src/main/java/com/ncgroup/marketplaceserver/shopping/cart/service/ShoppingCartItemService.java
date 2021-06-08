package com.ncgroup.marketplaceserver.shopping.cart.service;

import com.ncgroup.marketplaceserver.security.model.UserPrincipal;
import com.ncgroup.marketplaceserver.shopping.cart.exceptions.AccessDeniedException;
import com.ncgroup.marketplaceserver.shopping.cart.exceptions.NotFoundException;
import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemCreateDto;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemUpdateDto;

import java.util.Collection;

public interface ShoppingCartItemService {

    public ShoppingCartItem put(ShoppingCartItemCreateDto shoppingCartItemDto);

    public ShoppingCartItem update(long id,ShoppingCartItemUpdateDto shoppingCartItemDto) throws NotFoundException;

    public void delete(long id) throws NotFoundException;

    public void deleteAll();

    public ShoppingCartItem get(long id) throws NotFoundException;

    public Collection<ShoppingCartItem> getAll();

}
