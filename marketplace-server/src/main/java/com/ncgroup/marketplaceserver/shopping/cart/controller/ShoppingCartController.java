package com.ncgroup.marketplaceserver.shopping.cart.controller;

import com.ncgroup.marketplaceserver.shopping.cart.exceptions.AccessDeniedException;
import com.ncgroup.marketplaceserver.shopping.cart.exceptions.NotFoundException;
import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemCreateDto;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemUpdateDto;
import com.ncgroup.marketplaceserver.security.model.UserPrincipal;
import com.ncgroup.marketplaceserver.shopping.cart.service.ShoppingCartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping(
        path = "/api/shopping-cart",
        produces = "application/json"
)
public class ShoppingCartController {

    private ShoppingCartItemService service;

    @Autowired
    public ShoppingCartController(ShoppingCartItemService service) {
        this.service = service;
    }

    @PostMapping("/item")
    public ResponseEntity<ShoppingCartItem> createCartItem(
        @Valid @RequestBody ShoppingCartItemCreateDto shoppingCartItemCreateDto
    ){
        return new ResponseEntity<>(service.create(shoppingCartItemCreateDto), HttpStatus.CREATED);
    }

    @GetMapping("/item/{id}/")
    public ResponseEntity<ShoppingCartItem> readCartItem(
        @PathVariable("id") long id
    ) throws AccessDeniedException, NotFoundException {
        return new ResponseEntity<>(service.read(id), HttpStatus.OK);
    }

    @PatchMapping("/item/{id}/")
    public ResponseEntity<ShoppingCartItem> updateCartItem(
        @Valid @RequestBody ShoppingCartItemUpdateDto shoppingCartItemUpdateDto,
        @PathVariable("id") long id
    ) throws AccessDeniedException, NotFoundException {
        return new ResponseEntity<>(service.update(id,shoppingCartItemUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/item/{id}/")
    public ResponseEntity<?> deleteCartItem(
        @PathVariable("id") long id
    ) throws AccessDeniedException, NotFoundException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    public ResponseEntity<Collection<ShoppingCartItem>> getShoppingCart(){
        return new ResponseEntity<>(
            service.readAll(),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/")
    public ResponseEntity<?> emptyShoppingCart(){
        service.deleteAll();
        return ResponseEntity.noContent().build();
    }


}
