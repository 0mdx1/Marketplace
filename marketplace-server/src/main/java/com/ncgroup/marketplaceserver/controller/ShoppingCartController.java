package com.ncgroup.marketplaceserver.controller;

import com.ncgroup.marketplaceserver.model.ShoppingCartItem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping(
        path = "/api/shopping-cart",
        produces = "application/json"
)
public class ShoppingCartController {

    @PostMapping("/item")
    public ResponseEntity<ShoppingCartItem> createCartItem(){
        return new ResponseEntity<ShoppingCartItem>(new ShoppingCartItem(), HttpStatus.CREATED);
    }

    @GetMapping("/item/{id}/")
    public ResponseEntity<ShoppingCartItem> readCartItem(@PathVariable("id") long id){
        return new ResponseEntity<ShoppingCartItem>(new ShoppingCartItem(), HttpStatus.OK);
    }

    @PatchMapping("/item/{id}/")
    public ResponseEntity<ShoppingCartItem> updateCartItem(@PathVariable("id") long id){
        return new ResponseEntity<ShoppingCartItem>(new ShoppingCartItem(), HttpStatus.OK);
    }

    @DeleteMapping("/item/{id}/")
    public ResponseEntity<?> deleteCartItem(@PathVariable("id") long id){
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/")
    public ResponseEntity<Collection<ShoppingCartItem>> getShoppingCart(){
        return new ResponseEntity<Collection<ShoppingCartItem>>(new ArrayList<ShoppingCartItem>(),HttpStatus.OK);
    }

    @DeleteMapping("/")
    public ResponseEntity<?> emptyShoppingCart(){
        return ResponseEntity.noContent().build();
    }
}
