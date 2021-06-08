package com.ncgroup.marketplaceserver.shopping.cart.repository;

import com.ncgroup.marketplaceserver.model.Goods;
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
import java.util.Optional;

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
    public Optional<ShoppingCartItem> findByGoodsIdAndUserId(long goodsId, long userId) {
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
            return Optional.empty();
        }

        return Optional.of(res);
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

    private static Goods mapRowToGoods(ResultSet rs, int rowNum) throws SQLException {
        return Goods
                .builder()
                .id(rs.getLong("goods_id"))
                .name(rs.getString("name"))
                .category(rs.getString("category"))
                .description(rs.getString("description"))
                .image(rs.getString("image"))
                .price(rs.getInt("price"))
                .quantity(rs.getInt("goods_quantity"))
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
