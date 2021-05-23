package com.ncgroup.marketplaceserver.shopping.cart.service;

import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.security.model.UserPrincipal;
import com.ncgroup.marketplaceserver.service.UserService;
import com.ncgroup.marketplaceserver.shopping.cart.exceptions.AccessDeniedException;
import com.ncgroup.marketplaceserver.shopping.cart.exceptions.NotFoundException;
import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemCreateDto;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemUpdateDto;
import com.ncgroup.marketplaceserver.shopping.cart.repository.ShoppingCartItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    public ShoppingCartItem create(ShoppingCartItemCreateDto shoppingCartItemDto) {
        User user = userService.getCurrentUser();
        ShoppingCartItem shoppingCartItem = ShoppingCartItem
                .builder()
                .userId(user.getId())
                .addingTime(LocalDateTime.now())
                .build();
        shoppingCartItemDto.mapTo(shoppingCartItem);
        return repository.save(shoppingCartItem);
    }

    @Override
    public ShoppingCartItem update(long id, ShoppingCartItemUpdateDto shoppingCartItemDto) throws AccessDeniedException, NotFoundException {
        ShoppingCartItem shoppingCartItem = this.read(id);
        shoppingCartItemDto.mapTo(shoppingCartItem);
        return repository.update(shoppingCartItem);
    }

    @Override
    public void delete(long id) throws NotFoundException, AccessDeniedException {
        ShoppingCartItem shoppingCartItem = this.read(id);
        repository.remove(shoppingCartItem);
    }

    @Override
    public void deleteAll() {
        User user = userService.getCurrentUser();
        repository.removeAllByUser(user);
    }

    @Override
    public ShoppingCartItem read(long id) throws NotFoundException, AccessDeniedException {
        User user = userService.getCurrentUser();
        Optional<ShoppingCartItem> shoppingCartItemOpt = repository.findById(id);
        if(!shoppingCartItemOpt.isPresent()){
            throw new NotFoundException("Shopping cart item with id "+id+" not found");
        }
        ShoppingCartItem shoppingCartItem = shoppingCartItemOpt.get();
        if(!shoppingCartItem.belongsTo(user)){
            throw new AccessDeniedException("Access denied to shopping cart item with id "+id);
        }
        return shoppingCartItem;
    }

    @Override
    public Collection<ShoppingCartItem> readAll() {
        User user = userService.getCurrentUser();
        return repository.findAllByUser(user);
    }
}
