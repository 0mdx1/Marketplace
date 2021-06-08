package com.ncgroup.marketplaceserver.shopping.cart.repository;

import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;
import com.ncgroup.marketplaceserver.model.User;

import java.util.List;
import java.util.Optional;

public interface ShoppingCartItemRepository {

    public Optional<ShoppingCartItem> findByGoodsIdAndUserId(long goodsId, long userId);

    public List<ShoppingCartItem> findAllByUser(User user);

    public void save(ShoppingCartItem shoppingCartItem);

    public void update(ShoppingCartItem shoppingCartItem);

    public void remove(ShoppingCartItem shoppingCartItem);

    public void removeAllByUser(User user);

}
