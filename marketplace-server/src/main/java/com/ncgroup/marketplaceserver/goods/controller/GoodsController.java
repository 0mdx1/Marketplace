package com.ncgroup.marketplaceserver.goods.controller;

import com.ncgroup.marketplaceserver.goods.exceptions.GoodAlreadyExistsException;
import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.goods.model.GoodDto;
import com.ncgroup.marketplaceserver.goods.service.GoodsService;
import com.ncgroup.marketplaceserver.exception.basic.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/products")
public class GoodsController {

    private GoodsService service;

    @Autowired
    public GoodsController(GoodsService service) {
        this.service = service;
    }

    /**
     * create a product and return it just in case we need id/creationTime
     * on the client side
     */
    @PostMapping
    public ResponseEntity<Good> createProduct(@Valid @RequestBody GoodDto goodDto)
            throws GoodAlreadyExistsException {
        return new ResponseEntity<>(service.create(goodDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Good> editProduct(
            @Valid @RequestBody GoodDto goodDto, @PathVariable("id") long id)
            throws NotFoundException {
        return new ResponseEntity<>(service.edit(goodDto, id), HttpStatus.OK);
    }

    /**
     * displaying products with respect to filter,
     * sorting and page number if they are given,
     * otherwise just show the first page of all products (unsorted)
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> display(
            @RequestParam(value = "search", required = false)
                    String name,
            @RequestParam(value = "category", required = false)
                    String category,
            @RequestParam(value = "minPrice", required = false)
                    String minPrice,
            @RequestParam(value = "maxPrice", required = false)
                    String maxPrice,
            @RequestParam(value = "sort", required = false)
                    String sortBy,
            @RequestParam(value = "direction", required = false) // ASC or DESC
                    String sortDirection,
            @RequestParam(value = "page", required = false)
                    Integer page) throws NotFoundException {
        return new ResponseEntity<>(
                service.display(name, category, minPrice, maxPrice, sortBy,
                                sortDirection, page), HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getCategories() throws NotFoundException {
        return new ResponseEntity<>(service.getCategories(), HttpStatus.OK);
    }

    @GetMapping("/price-range/{category}")
    public ResponseEntity<List<Double>> getPriceRange( @PathVariable("category") String category) throws NotFoundException {
        return new ResponseEntity<>(service.getPriceRange(category), HttpStatus.OK);
    }

    @GetMapping("/firms")
    public ResponseEntity<List<String>> getFirms() throws NotFoundException {
        return new ResponseEntity<>(service.getFirms(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Good> getGood(@PathVariable("id") long id) throws NotFoundException {
        return new ResponseEntity<>(service.find(id), HttpStatus.OK);
    }
}

