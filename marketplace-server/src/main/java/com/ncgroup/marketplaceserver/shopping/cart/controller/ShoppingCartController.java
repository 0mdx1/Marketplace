package com.ncgroup.marketplaceserver.shopping.cart.controller;

import com.ncgroup.marketplaceserver.shopping.cart.exceptions.NotFoundException;
import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemCreateDto;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemUpdateDto;
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

    @PutMapping ("/item")
    public ResponseEntity<ShoppingCartItem> putCartItem(
        @Valid @RequestBody ShoppingCartItemCreateDto shoppingCartItemCreateDto
    ){
        return new ResponseEntity<>(service.put(shoppingCartItemCreateDto), HttpStatus.OK);
    }

    @GetMapping("/item/{id}/")
    public ResponseEntity<ShoppingCartItem> getCartItem(
        @PathVariable("id") long id
    ) throws NotFoundException {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @PatchMapping("/item/{id}/")
    public ResponseEntity<ShoppingCartItem> updateCartItem(
        @Valid @RequestBody ShoppingCartItemUpdateDto shoppingCartItemUpdateDto,
        @PathVariable("id") long id
    ) throws NotFoundException {
        return new ResponseEntity<>(service.update(id,shoppingCartItemUpdateDto), HttpStatus.OK);
    }

    @DeleteMapping("/item/{id}/")
    public ResponseEntity<?> deleteCartItem(
        @PathVariable("id") long id
    ) throws NotFoundException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    public ResponseEntity<Collection<ShoppingCartItem>> getShoppingCart(){
        return new ResponseEntity<>(
            service.getAll(),
            HttpStatus.OK
        );
    }

    @DeleteMapping("/")
    public ResponseEntity<?> emptyShoppingCart(){
        service.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
