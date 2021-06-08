package com.ncgroup.marketplaceserver.shopping.cart.service;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.service.UserService;
import com.ncgroup.marketplaceserver.shopping.cart.exceptions.NotFoundException;
import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemCreateDto;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemReadDto;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemUpdateDto;
import com.ncgroup.marketplaceserver.shopping.cart.repository.ShoppingCartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void put(ShoppingCartItemCreateDto shoppingCartItemDto) {
        User user = userService.getCurrentUser();
        Optional<ShoppingCartItem> shoppingCartItemOpt = repository.findByGoodsIdAndUserId(
                shoppingCartItemDto.getGoodsId(),
                user.getId()
        );
        if(shoppingCartItemOpt.isPresent()){
            ShoppingCartItem shoppingCartItem = shoppingCartItemOpt.get();
            shoppingCartItem.setQuantity(shoppingCartItemDto.getQuantity());
            repository.update(shoppingCartItem);
            return;
        }
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setUserId(user.getId());
        shoppingCartItemDto.mapTo(shoppingCartItem);
        repository.save(shoppingCartItem);
    }

    @Override
    public void update(long id, ShoppingCartItemUpdateDto shoppingCartItemDto) throws NotFoundException {
        ShoppingCartItem shoppingCartItem = this.getById(id);
        shoppingCartItemDto.mapTo(shoppingCartItem);
        repository.update(shoppingCartItem);
    }

    @Override
    public void delete(long id) throws NotFoundException{
        ShoppingCartItem shoppingCartItem = this.getById(id);
        repository.remove(shoppingCartItem);
    }

    @Override
    public void deleteAll() {
        User user = userService.getCurrentUser();
        repository.removeAllByUser(user);
    }

    @Override
    public ShoppingCartItemReadDto get(long id) throws NotFoundException{
        ShoppingCartItem shoppingCartItem = getById(id);
        return new ShoppingCartItemReadDto(shoppingCartItem);
    }

    private ShoppingCartItem getById(long id) throws NotFoundException {
        User user = userService.getCurrentUser();
        Optional<ShoppingCartItem> shoppingCartItemOpt = repository.findByGoodsIdAndUserId(id,user.getId());
        if(!shoppingCartItemOpt.isPresent()){
            throw new NotFoundException("Shopping cart item with goods id "+ id +" not found");
        }
        return shoppingCartItemOpt.get();
    }

    @Override
    public List<ShoppingCartItemReadDto> getAll() {
        User user = userService.getCurrentUser();
        return repository.findAllByUser(user).stream().map(ShoppingCartItemReadDto::new).collect(Collectors.toList());
    }
}
