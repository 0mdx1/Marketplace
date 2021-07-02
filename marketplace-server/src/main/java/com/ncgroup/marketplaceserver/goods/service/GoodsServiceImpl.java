package com.ncgroup.marketplaceserver.goods.service;

import com.ncgroup.marketplaceserver.exception.basic.NotFoundException;
import com.ncgroup.marketplaceserver.file.service.MediaService;
import com.ncgroup.marketplaceserver.goods.exceptions.GoodAlreadyExistsException;
import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.goods.model.GoodDto;
import com.ncgroup.marketplaceserver.goods.model.ModelView;
import com.ncgroup.marketplaceserver.goods.model.RequestParams;
import com.ncgroup.marketplaceserver.goods.repository.GoodsRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@PropertySource("classpath:application.properties")
public class GoodsServiceImpl implements GoodsService {

    @Value("${page.capacity}")
    private Integer PAGE_CAPACITY;

    private final GoodsRepository repository;
    private final MediaService mediaService;

    @Autowired
    public GoodsServiceImpl(GoodsRepository repository, MediaService mediaService) {
        this.repository = repository;
        this.mediaService = mediaService;
    }

    @Override
    public Good create(GoodDto goodDto) throws GoodAlreadyExistsException {
        String newImage = goodDto.getImage();

        if (newImage != null && !newImage.isEmpty()) {

            goodDto.setImage(this.mediaService.confirmUpload(newImage));
        }
        return new Good(goodDto, repository.getGoodId(goodDto), mediaService);
    }

    @Override
    public Good edit(GoodDto goodDto, long id) throws NotFoundException {
        Good good = this.findById(id); // pull the good object if exists

        String newImage = goodDto.getImage();

        if (newImage != null && !newImage.isEmpty()) {
            String oldImage = good.getImage();
            if (!oldImage.isEmpty() && !oldImage.equals(newImage)) {
                goodDto.setImage(this.mediaService.confirmUpload(newImage));
                mediaService.delete(oldImage);
            }
        }

        repository.editGood(goodDto, id); // push the changed good object
        good.setProperties(goodDto, id, mediaService);
        return good;
    }

    @Override
    public Good find(long id) throws NotFoundException {
        Good good = findById(id);
        // fixme ???
        good.setImage(mediaService.getCloudStorage().getResourceUrl(good.getImage()));
        return good;
    }

    private Good findById(long id) throws NotFoundException {
        Optional<Good> goodOptional = repository.findById(id);
        return goodOptional.orElseThrow(() ->
                new NotFoundException("Product with " + id + " not found."));
    }

    @Override
    public ModelView display(RequestParams params) throws NotFoundException {

        List<String> conditions = new ArrayList<>();

        StringJoiner fromQuery = new StringJoiner(" ");
        fromQuery.add(
                "FROM goods INNER JOIN product ON goods.prod_id = product.id " +
                        "INNER JOIN firm ON goods.firm_id = firm.id " +
                        "INNER JOIN category ON category.id = product.category_id");

        log.info(fromQuery.toString());

        if (params.getName() != null) {
            conditions.add("UPPER(product.name) LIKE UPPER(:name)");
        }

        if (params.getCategory() != null && !params.getCategory().equals("all")) {
            conditions.add("category.name = :category");
        }

        if (params.getMinPrice() != null) {
            conditions.add("price - price*discount/100 >= :minPrice");
        }

        if (params.getMaxPrice() != null) {
            conditions.add("price - price*discount/100 <= :maxPrice");
        }

        StringJoiner whereStatement = new StringJoiner(" AND ");

        if (!conditions.isEmpty()) {
            fromQuery.add("WHERE");
            for (String condition : conditions) {
                whereStatement.add(condition);
            }
        }

        log.info(whereStatement.toString());
        fromQuery.merge(whereStatement);
        fromQuery.add("AND status = true");

        int numOfGoods = repository
                .countGoods("SELECT COUNT(*) " + fromQuery, params);

        if (params.getSort() != null) {
            switch (params.getSort()) {
                case PRICE:
                    fromQuery.add("ORDER BY goods.price");
                    break;
                case DATE:
                    fromQuery.add("ORDER BY shipping_date");
                    break;
                default:
                    fromQuery.add("ORDER BY product.name");
            }
        } else {
            fromQuery.add("ORDER BY product.name");
        }

        if (params.getDirection() != null && params.getDirection().equals("ASC")) {
            fromQuery.add("ASC");
        } else {
            fromQuery.add("DESC");
        }

        StringJoiner flexibleQuery = new StringJoiner(" ");
        flexibleQuery.add(
                "SELECT goods.id, product.name AS product_name, status, shipping_date, " +
                        "firm.name AS firm_name, category.name AS category_name, unit, " +
                        "goods.quantity, goods.price, goods.discount, goods.in_stock, " +
                        "goods.description, goods.image");
        flexibleQuery.merge(fromQuery);

        int numOfPages = numOfGoods % PAGE_CAPACITY == 0 ?
                numOfGoods / PAGE_CAPACITY : (numOfGoods / PAGE_CAPACITY) + 1;

        if (params.getPage() != null) {
            flexibleQuery.add("LIMIT :PAGE_CAPACITY OFFSET (:page - 1) * :PAGE_CAPACITY");
        } else {
            flexibleQuery.add("LIMIT :PAGE_CAPACITY");
            params.setPage(1);
        }

        List<Good> res = repository.display(flexibleQuery.toString(), params);

        if (res.isEmpty()) {
            throw new NotFoundException("Sorry, but there are no products corresponding to your criteria.");
        }

        for (Good good : res) {
            // fixme
            good.setImage(good.getImage(), mediaService);
        }

        return new ModelView(params.getPage(), numOfPages, res);

    }

    @Override
    public List<String> getCategories() throws NotFoundException {
        return repository.getCategories();
    }

    @Override
    public List<Double> getPriceRange(String category) throws NotFoundException {
        ArrayList<Double> priceRange = new ArrayList<>(2);
        if (category.equals("all")) {
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
    public void updateQuantity(long id, double quantity) {
        // check
        repository.editQuantity(id, quantity, Double.compare(quantity, 0) != 0);
    }
}
