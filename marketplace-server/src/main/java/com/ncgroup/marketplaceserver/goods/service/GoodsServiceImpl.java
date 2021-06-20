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

    static final Integer PAGE_CAPACITY = 12;

    private GoodsRepository repository;
    private MediaService mediaService;

    @Autowired
    public GoodsServiceImpl(GoodsRepository repository, MediaService mediaService) {
        this.repository = repository;
        this.mediaService = mediaService;
    }

    @Override
    public Good create(GoodDto goodDto) throws GoodAlreadyExistsException {
//        log.info("" + goodDto.getShippingDate());
        String newImage = goodDto.getImage();
        if (!newImage.isEmpty()){
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
        if (!newImage.isEmpty()) {
            String oldImage = good.getImage();
            goodDto.setImage(this.mediaService.confirmUpload(newImage));
            if (!oldImage.isEmpty() && !oldImage.equals(newImage)) {
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


        StringBuilder fromQuery = new StringBuilder("FROM goods INNER JOIN " +
                "product ON goods.prod_id = product.id " +
                "INNER JOIN firm ON goods.firm_id = firm.id " +
                "INNER JOIN category ON category.id = product.category_id");

//        log.info("Name " + name);;
        if (name != null) {
            concatenator.add(" product.name LIKE '%" + name.toLowerCase() + "%'");
            counter++;
        }

        if (category != null && !category.equals("all")) {
            concatenator.add(" category.name = " + "'" + category + "'");
            counter++;
        }

        if (minPrice != null) {
            concatenator.add(" price - price*discount/100 >= " + minPrice);
            counter++;
        }

        if (maxPrice != null) {
            concatenator.add(" price - price*discount/100 <= " + maxPrice);
            counter++;
        }


        if (counter > 0) {
            fromQuery.append(" WHERE").append(concatenator.get(0));
            for (int i = 1; i < counter; i++) {
//                log.info(concatenator.get(i));
                fromQuery.append(" AND").append(concatenator.get(i));
            }
        }

        fromQuery.append(" AND status = true");
        
//        log.info("SELECT COUNT(*) " + fromQuery);
        int numOfGoods = repository.countGoods("SELECT COUNT(*) " + fromQuery);

        if (sortBy != null) {
            switch (sortBy) {
                case "price":
                    fromQuery.append(" ORDER BY goods.price");
                    break;
                case "name":
                    fromQuery.append(" ORDER BY product.name");
                    break;
                case "date":
                    fromQuery.append(" ORDER BY shipping_date");
                    break;
            }
        } else {
            fromQuery.append(" ORDER BY product.name");
        }

        log.info("DIRECTION " + sortDirection);
        if (sortDirection != null) {
            fromQuery.append(" ").append(sortDirection.toUpperCase());
        } else {
            fromQuery.append(" DESC");
        }


        StringBuilder flexibleQuery = new StringBuilder
                ("SELECT goods.id, product.name AS product_name, status, shipping_date, " +
                        "firm.name AS firm_name, category.name AS category_name, unit, " +
                        " goods.quantity, goods.price, goods.discount, goods.in_stock," +
                        " goods.description, goods.image ");


        flexibleQuery.append(fromQuery);

//        log.info(fromQuery.toString());
//        log.info(flexibleQuery.toString());


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
            good.setImage(mediaService.getCloudStorage().getResourceUrl(good.getImage()));
        }


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
    public List<Double> getPriceRange(String category) throws NotFoundException {
        ArrayList<Double> priceRange = new ArrayList<>();
        if(category.equals("all")){
            priceRange.add(repository.getTotalMinPrice());
            priceRange.add(repository.getTotalMaxPrice());
            return priceRange;
        }
        priceRange.add(repository.getMinPrice(category));
        priceRange.add(repository.getMaxPrice(category));
        return priceRange;
    }

    @Override
    public List<String> getFirms() throws NotFoundException {
        return repository.getFirms();
    }

    @Override
    public void updateQuantity(long id, int quantity) {
        repository.editQuantity(id, quantity);
    }
}
