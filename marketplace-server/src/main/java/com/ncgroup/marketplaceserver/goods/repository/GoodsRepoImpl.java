package com.ncgroup.marketplaceserver.goods.repository;

import com.ncgroup.marketplaceserver.goods.exceptions.GoodAlreadyExistsException;
import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.goods.model.GoodDto;
import com.ncgroup.marketplaceserver.goods.model.Unit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.webjars.NotFoundException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@PropertySource("classpath:database/productQueries.properties")
@Repository
@Slf4j
public class GoodsRepoImpl implements GoodsRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public GoodsRepoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Value("${firm.find-by-name}")
    private String findFirmByName;
    @Value("${category.find-by-name}")
    private String findCategoryByName;
    @Value("${fake-product.find-by-name}")
    private String findProductByName;

    public Optional<Long> findByName(String name, String paramName, String sqlQuery) {
        SqlParameterSource parameter = new MapSqlParameterSource()
                .addValue(paramName, name);
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate
                    .queryForObject(sqlQuery, parameter, Long.class));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Value("${firm.insert}")
    private String firmInsert;
    public Long createFirm(String firmName) {
        Optional<Long> firmId = findByName
                (firmName, "firmName", findFirmByName);
        if (!firmId.isPresent()) {
            SqlParameterSource firmParameters = new MapSqlParameterSource()
                    .addValue("firmName", firmName);
            KeyHolder firmHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(firmInsert, firmParameters, firmHolder);
            firmId = Optional.of(firmHolder.getKey().longValue());
        }
        return firmId.get();
    }

    @Value("${category.insert}")
    private String categoryInsert;
    public Long createCategory(String categoryName) {
        Optional<Long> categoryId = findByName
                (categoryName, "categoryName", findCategoryByName);
        if (!categoryId.isPresent()) {
            SqlParameterSource categoryParameters = new MapSqlParameterSource()
                    .addValue("categoryName", categoryName);
            KeyHolder categoryHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(categoryInsert, categoryParameters, categoryHolder);
            categoryId = Optional.of(categoryHolder.getKey().longValue());
        }
        return categoryId.get();
    }

    @Value("${product.insert}")
    private String productInsert;
    public Long createProduct(String goodName, Long categoryId) {
        Optional<Long> productId = findByName
                (goodName, "productName", findProductByName);
        if (!productId.isPresent()) {
            KeyHolder productHolder = new GeneratedKeyHolder();
            SqlParameterSource productParameters = new MapSqlParameterSource()
                    .addValue("productName", goodName)
                    .addValue("categoryId", categoryId);
            namedParameterJdbcTemplate.update(productInsert, productParameters, productHolder);
            productId = Optional.of(productHolder.getKey().longValue());
        }
        return productId.get();
    }


    // TODO: add shipping date here
    @Value("${good.find-by-firmId-productId}")
    private String findGood;
    public Optional<Long> findGood(Long firmId, Long productId) {
        SqlParameterSource goodParameters = new MapSqlParameterSource()
                .addValue("firmId", firmId)
                .addValue("productId", productId);
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate
                    .queryForObject(findGood, goodParameters, Long.class));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Value("${good.insert}")
    private String goodInsert;
    @Override
    public Long createGood(GoodDto goodDto)
            throws GoodAlreadyExistsException {
        // TODO: make changes with status, shipping date and unit fields

        Long firmId = createFirm(goodDto.getFirmName().toLowerCase());
        Long categoryId = createCategory(goodDto.getCategoryName().toLowerCase());
        Long productId = createProduct(goodDto.getGoodName().toLowerCase(), categoryId);

        /**
         * goods are equal if their firm,
         * product and shipping date are equal
         */

        Optional<Long> goodId = findGood(firmId, productId);
        if (!goodId.isPresent()) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            SqlParameterSource goodParameters = new MapSqlParameterSource()
                    .addValue("goodQuantity", goodDto.getQuantity())
                    .addValue("goodPrice", goodDto.getPrice())
                    .addValue("goodDiscount", goodDto.getDiscount())
                    .addValue("goodInStock", goodDto.isInStock())
                    .addValue("goodDescription", goodDto.getDescription())

                    .addValue("unit", goodDto.getUnit().toString())

                    //.addValue("date", goodDto.getShippingDate())

                    .addValue("productId", productId)
                    .addValue("firmId", firmId);
            namedParameterJdbcTemplate.update(goodInsert, goodParameters, keyHolder);
            return keyHolder.getKey().longValue();
        }
        throw new GoodAlreadyExistsException
                ("Such good already exists! " +
                        "If you want to modify an existing good," +
                        " please go to the list of goods, select good and click edit.");
    }

    @Value("${product.update}")
    private String updateProduct;
    @Override
    public void editGood(GoodDto goodDto, Long id) {
        Long firmId = createFirm(goodDto.getFirmName().toLowerCase());
        Long categoryId = createCategory(goodDto.getCategoryName().toLowerCase());
        Long productId = createProduct(goodDto.getGoodName().toLowerCase(), categoryId);

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("id", id) // for search purpose
                .addValue("prodId", productId)
                .addValue("firmId", firmId)
                .addValue("quantity", goodDto.getQuantity())
                .addValue("price", goodDto.getPrice())
                .addValue("discount", goodDto.getDiscount())
                .addValue("inStock", goodDto.isInStock())

                .addValue("unit", goodDto.getUnit().toString())

                .addValue("description", goodDto.getDescription());
        namedParameterJdbcTemplate.update(updateProduct, parameters);
    }

    @Value("${good.find-by-id}")
    private String findGoodById;
    @Override
    public Optional<Good> findById(long id) {
        SqlParameterSource productParameter = new MapSqlParameterSource()
                .addValue("goodId", id);
        Good good;
        // TODO: QUESTION: specification + Optional.ofNullable()
        try {
            good = namedParameterJdbcTemplate
                    .queryForObject(findGoodById, productParameter, this::mapRow);
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
        return Optional.ofNullable(good);
    }

    @Override
    public List<Good> display(String query) {
        return namedParameterJdbcTemplate.query(
                query,
                this::mapRow
        );
    }

    @Value("${categories.get}")
    String getCategories;
    @Override
    public List<String> getCategories() throws NotFoundException{
        List<String> res = namedParameterJdbcTemplate.query(getCategories,
                (resultSet, i) -> resultSet.getString("name"));
        if (res.isEmpty())
            throw new NotFoundException("Sorry, but there are no categories yet.");
        return res;
    }

    private Good mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Good.builder()
                .id(rs.getLong("id"))

                //.shippingDate(rs.getObject("shipping_date", LocalDate.class))

                .unit(Unit.valueOf(rs.getString("unit")))

                .quantity(rs.getInt("quantity"))
                .categoryName(rs.getString("category_name"))
                .goodName(rs.getString("product_name"))
                .firmName(rs.getString("firm_name"))
                .price(rs.getDouble("price"))
                .discount(rs.getByte("discount"))
                .inStock(rs.getBoolean("in_stock"))
                .description(rs.getString("description"))
                .build();
    }
}
