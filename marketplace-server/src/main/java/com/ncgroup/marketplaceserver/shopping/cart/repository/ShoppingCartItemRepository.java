package com.ncgroup.marketplaceserver.shopping.cart.repository;

import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;
import com.ncgroup.marketplaceserver.model.User;

import java.util.Collection;
import java.util.Optional;

public interface ShoppingCartItemRepository {

    public Optional<ShoppingCartItem> findById(long id);

    public Collection<ShoppingCartItem> findAllByUser(User user);

    public ShoppingCartItem save(ShoppingCartItem shoppingCartItem);

    public ShoppingCartItem update(ShoppingCartItem shoppingCartItem);

    public void remove(ShoppingCartItem shoppingCartItem);

    public void removeAllByUser(User user);

}
