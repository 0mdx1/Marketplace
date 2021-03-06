package com.ncgroup.marketplaceserver.shopping.cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ncgroup.marketplaceserver.exception.basic.NotFoundException;
import com.ncgroup.marketplaceserver.file.service.MediaService;
import com.ncgroup.marketplaceserver.model.User;
import com.ncgroup.marketplaceserver.service.UserService;
import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemCreateDto;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemReadDto;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemUpdateDto;
import com.ncgroup.marketplaceserver.shopping.cart.repository.ShoppingCartItemRepository;

@Service
public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {

    private ShoppingCartItemRepository repository;
    private UserService userService;
    private MediaService mediaService;

    public ShoppingCartItemServiceImpl(ShoppingCartItemRepository repository, UserService userService, MediaService mediaService) {
        this.repository = repository;
        this.userService = userService;
        this.mediaService = mediaService;
    }

    @Override
    public void put(ShoppingCartItemCreateDto shoppingCartItemDto) {
        User user = userService.getCurrentUser();
        ShoppingCartItem shoppingCartItem = repository.findByGoodsIdAndUserId(
                shoppingCartItemDto.getGoodsId(),
                user.getId()
        );
        if (shoppingCartItem != null) {
            shoppingCartItem.setQuantity(shoppingCartItemDto.getQuantity());
            repository.update(shoppingCartItem);
            return;
        }
        shoppingCartItem = new ShoppingCartItem();
        shoppingCartItem.setUserId(user.getId());
        shoppingCartItemDto.projectOnto(shoppingCartItem);
        repository.save(shoppingCartItem);
    }

    @Override
    public void update(long id, ShoppingCartItemUpdateDto shoppingCartItemDto) throws NotFoundException {
        ShoppingCartItem shoppingCartItem = this.getById(id);
        shoppingCartItemDto.mapTo(shoppingCartItem);
        repository.update(shoppingCartItem);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        ShoppingCartItem shoppingCartItem = this.getById(id);
        repository.remove(shoppingCartItem);
    }

    @Override
    public void deleteAll() {
        User user = userService.getCurrentUser();
        repository.removeAllByUser(user);
    }

    @Override
    public ShoppingCartItemReadDto get(long id) throws NotFoundException {
        ShoppingCartItem shoppingCartItem = getById(id);
        String imageUrl = this.mediaService.getCloudStorage().getResourceUrl(shoppingCartItem.getGoods().getImage());
        shoppingCartItem.getGoods().setImage(imageUrl);
        return new ShoppingCartItemReadDto(shoppingCartItem);
    }

    private ShoppingCartItem getById(long id) throws NotFoundException {
        User user = userService.getCurrentUser();
        ShoppingCartItem shoppingCartItem = repository.findByGoodsIdAndUserId(id, user.getId());
        if (shoppingCartItem == null) {
            throw new NotFoundException("Shopping cart item with goods id " + id + " not found");
        }
        return shoppingCartItem;
    }

    @Override
    public List<ShoppingCartItemReadDto> getAll() {
        User user = userService.getCurrentUser();
        return repository.findAllByUser(user)
                .stream()
                .peek(item -> {
                    String imageUrl = this.mediaService.getCloudStorage().getResourceUrl(item.getGoods().getImage());
                    item.getGoods().setImage(imageUrl);
                })
                .map(ShoppingCartItemReadDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void setAll(List<ShoppingCartItemCreateDto> shoppingCartItemCreateDtos) {
        this.deleteAll();
        for (ShoppingCartItemCreateDto shoppingCartItemCreateDto : shoppingCartItemCreateDtos) {
            this.put(shoppingCartItemCreateDto);
        }
    }
}
