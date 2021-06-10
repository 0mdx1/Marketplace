package com.ncgroup.marketplaceserver.shopping.cart.controller;

import com.ncgroup.marketplaceserver.exception.domain.NotFoundException;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemCreateDto;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemReadDto;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemUpdateDto;
import com.ncgroup.marketplaceserver.shopping.cart.service.ShoppingCartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(
        path = "/api/shopping-cart",
        produces = "application/json"
)
@Validated
public class ShoppingCartController {

    private ShoppingCartItemService service;

    @Autowired
    public ShoppingCartController(ShoppingCartItemService service) {
        this.service = service;
    }

    @PutMapping ("/item/")
    public ResponseEntity<?> putCartItem(
        @Valid @RequestBody ShoppingCartItemCreateDto shoppingCartItemCreateDto
    ){
        service.put(shoppingCartItemCreateDto);
        return ResponseEntity.status(HttpStatus.OK).build();

    }

    @GetMapping("/item/{id}/")
    public ResponseEntity<ShoppingCartItemReadDto> getCartItem(
        @PathVariable("id") long id
    ) throws NotFoundException {
        return new ResponseEntity<>(service.get(id), HttpStatus.OK);
    }

    @PatchMapping("/item/{id}/")
    public ResponseEntity<?> updateCartItem(
        @Valid @RequestBody ShoppingCartItemUpdateDto shoppingCartItemUpdateDto,
        @PathVariable("id") long id
    ) throws NotFoundException {
        service.update(id,shoppingCartItemUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/item/{id}/")
    public ResponseEntity<?> deleteCartItem(
        @PathVariable("id") long id
    ) throws NotFoundException {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    public ResponseEntity<List<ShoppingCartItemReadDto>> getShoppingCart(){
        return new ResponseEntity<>(
            service.getAll(),
            HttpStatus.OK
        );
    }

    @PutMapping("/")
    public ResponseEntity<?> putShoppingCart(@RequestBody @Valid List<ShoppingCartItemCreateDto> items){
        this.service.setAll(items);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/")
    public ResponseEntity<?> emptyShoppingCart(){
        service.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate/")
    public ResponseEntity<?> validateShoppingCart(@RequestBody @Valid List<ShoppingCartItemCreateDto> items){
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
