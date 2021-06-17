
package com.ncgroup.marketplaceserver.goods.service;

import com.ncgroup.marketplaceserver.exception.basic.NotFoundException;
import com.ncgroup.marketplaceserver.file.service.MediaService;
import com.ncgroup.marketplaceserver.goods.exceptions.GoodAlreadyExistsException;
import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.goods.model.GoodDto;
import com.ncgroup.marketplaceserver.goods.repository.GoodsRepository;



import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    static final Integer PAGE_CAPACITY = 10;

    private GoodsRepository repository;

    private MediaService mediaService;

    @Autowired
    public GoodsServiceImpl(GoodsRepository repository, MediaService mediaService) {
        this.repository = repository;
        this.mediaService = mediaService;
    }

    @Override
    public Good create(GoodDto goodDto) throws GoodAlreadyExistsException {
        String newImage = goodDto.getImage();
        if(!newImage.isEmpty()){
            goodDto.setImage(this.mediaService.confirmUpload(newImage));
        }
        Long goodId = repository.createGood(goodDto); // get the id of new good if it is new
        Good good = new Good();
        good.setProperties(goodDto, goodId);
        good.setImage(mediaService.getCloudStorage().getResourceUrl(good.getImage()));
        return good;
    }

    @Override
    public Good edit(GoodDto goodDto, long id) throws NotFoundException {
        Good good = this.findById(id); // pull the good object if exists
        String newImage = goodDto.getImage();
        if(!newImage.isEmpty()){
            String oldImage = good.getImage();
            goodDto.setImage(this.mediaService.confirmUpload(newImage));
            if(!oldImage.isEmpty() && !oldImage.equals(newImage)){
                log.info("Deleting old image");
                mediaService.delete(oldImage);
            }
        }
        good.setProperties(goodDto, id);
        repository.editGood(goodDto, id); // push the changed good object
        good.setImage(mediaService.getCloudStorage().getResourceUrl(good.getImage()));
        return good;
    }

    @Override
    public Good find(long id) throws NotFoundException {
        Good good = findById(id);
        good.setImage(mediaService.getCloudStorage().getResourceUrl(good.getImage()));
        return good;
    }

    private Good findById(long id) throws NotFoundException{
        Optional<Good> goodOptional = repository.findById(id);
        return goodOptional.orElseThrow(() -> new NotFoundException("Product with " + id + " not found."));
    }

    @Override
    public Map<String, Object> display
            (String name, String category, String minPrice, String maxPrice,
             String sortBy, String sortDirection, Integer page) throws NotFoundException {

        int counter = 0;
        List<String> concatenator = new ArrayList<>();

        StringBuilder flexibleQuery = new StringBuilder
                ("SELECT goods.id, product.name AS product_name, " +
                "firm.name AS firm_name, category.name AS category_name, unit, " +
                " goods.quantity, goods.price, goods.discount, goods.in_stock," +
                " goods.description, goods.image ");

        String fromQuery = "FROM goods INNER JOIN " +
                "product ON goods.prod_id = product.id " +
                "INNER JOIN firm ON goods.firm_id = firm.id " +
                "INNER JOIN category ON category.id = product.category_id";

        // Sort can be by: price, product.name, discount.

        flexibleQuery.append(fromQuery);


        if (name != null) {
            concatenator.add(" product.name LIKE '%" + name.toLowerCase() + "%'");
            counter++;
        }

        if (category != null && !category.equals("all")) {
            concatenator.add(" category.name = " + "'" + category + "'");
            counter++;
        }

        if (minPrice != null) {
            concatenator.add(" price >= " + minPrice);
            counter++;
        }

        if (maxPrice != null) {
            concatenator.add(" price <= " + maxPrice);
            counter++;
        }


        if (counter > 0) {
            flexibleQuery.append(" WHERE").append(concatenator.get(0));
            for (int i = 1; i < counter; i++) {
                flexibleQuery.append(" AND").append(concatenator.get(i));
            }
        }


        if (sortBy != null) {
            if(sortBy.equals("price")) {
                flexibleQuery.append(" ORDER BY goods.price");
            } else if (sortBy.equals("name")) {
                flexibleQuery.append(" ORDER BY product.name");
            }
        } else {
            flexibleQuery.append(" ORDER BY product.name");
        }

        if (sortDirection != null) {
            flexibleQuery.append(" ").append(sortDirection.toUpperCase());
        } else {
            flexibleQuery.append(" DESC");
        }

        int numOfGoods = repository.countGoods("SELECT COUNT(*) " + fromQuery);

        int numOfPages = numOfGoods % PAGE_CAPACITY == 0 ?
                numOfGoods / PAGE_CAPACITY : (numOfGoods / PAGE_CAPACITY) + 1;

        if (page != null) {
            flexibleQuery.append(" LIMIT " + PAGE_CAPACITY + " OFFSET ")
                    .append((page - 1) * PAGE_CAPACITY);
        } else {
            flexibleQuery.append(" LIMIT " + PAGE_CAPACITY);
            page = 1;
        }

        List<Good> res = repository.display(flexibleQuery.toString());
        if (res.isEmpty()) {
            throw new NotFoundException
                    ("Sorry, but there are no products corresponding to your criteria.");
        }

        for (Good good : res) {
            good.setPrice(good.getPrice(), good.getDiscount());
            good.setImage(mediaService.getCloudStorage().getResourceUrl(good.getImage()));
        }

//        if (page != null) {
//            res = res.subList(
//                    (page - 1) * PAGE_CAPACITY,
//                    Math.min(res.size(), (page - 1) * PAGE_CAPACITY + PAGE_CAPACITY));
//        } else {
//            page = 1;
//            res = res.subList(0, Math.min(res.size(), PAGE_CAPACITY));
//        }
//        for (Good good : res) {
//            good.setPrice(good.getPrice(), good.getDiscount());
//        }

        Map<String, Object> response = new HashMap<>();
        response.put("current", page);
        response.put("total", numOfPages);
        response.put("result_set", res);

        return response;
    }

    @Override
    public List<String> getCategories() throws NotFoundException {
        return repository.getCategories();
    }

    @Override
    public List<String> getFirms() throws NotFoundException {
        return repository.getFirms();
    }
    
    @Override
    public void updateQuantity(long id, int qunatity) {
    	repository.editQuantity(id, qunatity);
    }
}
