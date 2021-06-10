package com.ncgroup.marketplaceserver.shopping.cart.repository;

import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.goods.model.Unit;
import com.ncgroup.marketplaceserver.shopping.cart.model.ShoppingCartItem;
import com.ncgroup.marketplaceserver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@PropertySource("classpath:database/queries.properties")
@Repository
public class ShoppingCartItemRepositoryImpl implements ShoppingCartItemRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public ShoppingCartItemRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Value("${shopping-cat-item.select-by-ids-query}")
    private String selectByIdsQuery;

    @Override
    public ShoppingCartItem findByGoodsIdAndUserId(long goodsId, long userId) {
        SqlParameterSource shoppingCartItemParams = new MapSqlParameterSource()
                .addValue("goodsId", goodsId)
                .addValue("userId",userId);
        ShoppingCartItem res;
        try {
            res = namedParameterJdbcTemplate.queryForObject(
                    selectByIdsQuery,
                    shoppingCartItemParams,
                    ShoppingCartItemRepositoryImpl::mapRowToShoppingCartItem
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        return res;
    }

    @Value("${shopping-cat-item.select-by-user-id-query}")
    private String selectByUserIdQuery;

    @Override
    public List<ShoppingCartItem> findAllByUser(User user) {
        SqlParameterSource shoppingCartItemParams = new MapSqlParameterSource()
                .addValue("userId", user.getId());
        return namedParameterJdbcTemplate.query(
                selectByUserIdQuery,
                shoppingCartItemParams,
                ShoppingCartItemRepositoryImpl::mapRowToShoppingCartItem
        );
    }

    @Value("${shopping-cat-item.insert-query}")
    private String insertQuery;

    @Override
    public void save(ShoppingCartItem shoppingCartItem) {
        SqlParameterSource shoppingCartItemParams = new MapSqlParameterSource()
                .addValue("userId", shoppingCartItem.getUserId())
                .addValue("goodsId", shoppingCartItem.getGoods().getId())
                .addValue("quantity", shoppingCartItem.getQuantity())
                .addValue("addingTime", shoppingCartItem.getAddingTime());
        namedParameterJdbcTemplate.update(insertQuery,shoppingCartItemParams);
    }

    @Value("${shopping-cat-item.update-by-ids-query}")
    private String updateByIdsQuery;

    @Override
    public void update(ShoppingCartItem shoppingCartItem) {
        SqlParameterSource shoppingCartItemParams = new MapSqlParameterSource()
                .addValue("userId", shoppingCartItem.getUserId())
                .addValue("goodsId", shoppingCartItem.getGoods().getId())
                .addValue("quantity", shoppingCartItem.getQuantity())
                .addValue("addingTime", shoppingCartItem.getAddingTime());
        namedParameterJdbcTemplate.update(updateByIdsQuery,shoppingCartItemParams);
    }

    @Value("${shopping-cat-item.delete-by-ids-query}")
    private String deleteByIdsQuery;

    @Override
    public void remove(ShoppingCartItem shoppingCartItem) {
        SqlParameterSource shoppingCartItemParams = new MapSqlParameterSource()
                .addValue("userId", shoppingCartItem.getUserId())
                .addValue("goodsId", shoppingCartItem.getGoods().getId());
        namedParameterJdbcTemplate.update(
                deleteByIdsQuery,
                shoppingCartItemParams
        );
    }

    @Value("${shopping-cat-item.delete-by-user-id-query}")
    private String deleteByUserIdQuery;

    @Override
    public void removeAllByUser(User user) {
        SqlParameterSource shoppingCartItemParams = new MapSqlParameterSource()
                .addValue("userId", user.getId());
        namedParameterJdbcTemplate.update(
                deleteByUserIdQuery,
                shoppingCartItemParams
        );
    }

    private static Good mapRowToGoods(ResultSet rs, int rowNum) throws SQLException {
        return Good.builder()
                .id(rs.getLong("goods_id"))
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

    private static ShoppingCartItem mapRowToShoppingCartItem(ResultSet rs, int rowNum) throws SQLException {
        return ShoppingCartItem
                .builder()
                .userId(rs.getLong("user_id"))
                .goods(mapRowToGoods(rs,rowNum))
                .quantity(rs.getInt("quantity"))
                .addingTime(rs.getLong("adding_time"))
                .build();
    }
}