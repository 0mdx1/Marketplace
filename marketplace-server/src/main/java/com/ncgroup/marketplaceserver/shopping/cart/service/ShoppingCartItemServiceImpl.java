package com.ncgroup.marketplaceserver.shopping.cart.service;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.service.UserService;
import com.ncgroup.marketplaceserver.shopping.cart.exceptions.NotFoundException;
import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemCreateDto;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemUpdateDto;
import com.ncgroup.marketplaceserver.shopping.cart.repository.ShoppingCartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService{

    private ShoppingCartItemRepository repository;
    private UserService userService;

    @Autowired
    public ShoppingCartItemServiceImpl(ShoppingCartItemRepository repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public ShoppingCartItem put(ShoppingCartItemCreateDto shoppingCartItemDto) {
        User user = userService.getCurrentUser();
        Optional<ShoppingCartItem> shoppingCartItemOpt = repository.findByGoodsIdAndUserId(
                shoppingCartItemDto.getGoodsId(),
                user.getId()
        );
        if(shoppingCartItemOpt.isPresent()){
            ShoppingCartItem shoppingCartItem = shoppingCartItemOpt.get();
            shoppingCartItem.setQuantity(shoppingCartItem.getQuantity()+shoppingCartItemDto.getQuantity());
            return repository.update(shoppingCartItem);
        }else{
            ShoppingCartItem shoppingCartItem = ShoppingCartItem
                    .builder()
                    .userId(user.getId())
                    .goodsId(shoppingCartItemDto.getGoodsId())
                    .quantity(shoppingCartItemDto.getQuantity())
                    .addingTime(shoppingCartItemDto.getAddingTime())
                    .build();
            return repository.save(shoppingCartItem);
        }
    }

    @Override
    public ShoppingCartItem update(long id, ShoppingCartItemUpdateDto shoppingCartItemDto) throws NotFoundException {
        ShoppingCartItem shoppingCartItem = this.get(id);
        shoppingCartItemDto.mapTo(shoppingCartItem);
        return repository.update(shoppingCartItem);
    }

    @Override
    public void delete(long id) throws NotFoundException{
        ShoppingCartItem shoppingCartItem = this.get(id);
        repository.remove(shoppingCartItem);
    }

    @Override
    public void deleteAll() {
        User user = userService.getCurrentUser();
        repository.removeAllByUser(user);
    }

    @Override
    public ShoppingCartItem get(long id) throws NotFoundException{
        User user = userService.getCurrentUser();
        Optional<ShoppingCartItem> shoppingCartItemOpt = repository.findByGoodsIdAndUserId(id,user.getId());
        if(!shoppingCartItemOpt.isPresent()){
            throw new NotFoundException("Shopping cart item with goods id "+id+" not found");
        }
        return shoppingCartItemOpt.get();
    }

    @Override
    public Collection<ShoppingCartItem> getAll() {
        User user = userService.getCurrentUser();
        return repository.findAllByUser(user);
    }
}
