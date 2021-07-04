package com.ncgroup.marketplaceserver.shopping.cart.repository;

import java.util.List;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;

public interface ShoppingCartItemRepository {

    ShoppingCartItem findByGoodsIdAndUserId(long goodsId, long userId);

    List<ShoppingCartItem> findAllByUser(User user);

    void save(ShoppingCartItem shoppingCartItem);

    void update(ShoppingCartItem shoppingCartItem);

    void remove(ShoppingCartItem shoppingCartItem);

    void removeAllByUser(User user);

}
