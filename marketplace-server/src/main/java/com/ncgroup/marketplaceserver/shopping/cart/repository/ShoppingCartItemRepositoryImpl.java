package com.ncgroup.marketplaceserver.shopping.cart.repository;

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
                    ShoppingCartItemRepositoryImpl::mapRow
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
                ShoppingCartItemRepositoryImpl::mapRow
        );
    }

    @Value("${shopping-cat-item.insert-query}")
    private String insertQuery;

    @Override
    public ShoppingCartItem save(ShoppingCartItem shoppingCartItem) {
        SqlParameterSource shoppingCartItemParams = new MapSqlParameterSource()
                .addValue("userId", shoppingCartItem.getUserId())
                .addValue("goodsId", shoppingCartItem.getGoodsId())
                .addValue("quantity", shoppingCartItem.getQuantity())
                .addValue("addingTime", shoppingCartItem.getAddingTime());
        return namedParameterJdbcTemplate.queryForObject(
                insertQuery,
                shoppingCartItemParams,
                ShoppingCartItemRepositoryImpl::mapRow
        );
    }

    @Value("${shopping-cat-item.update-by-ids-query}")
    private String updateByIdsQuery;

    @Override
    public ShoppingCartItem update(ShoppingCartItem shoppingCartItem) {
        SqlParameterSource shoppingCartItemParams = new MapSqlParameterSource()
                .addValue("userId", shoppingCartItem.getUserId())
                .addValue("goodsId", shoppingCartItem.getGoodsId())
                .addValue("quantity", shoppingCartItem.getQuantity())
                .addValue("addingTime", shoppingCartItem.getAddingTime());
        return namedParameterJdbcTemplate.queryForObject(
                updateByIdsQuery,
                shoppingCartItemParams,
                ShoppingCartItemRepositoryImpl::mapRow
        );
    }

    @Value("${shopping-cat-item.delete-by-ids-query}")
    private String deleteByIdsQuery;

    @Override
    public void remove(ShoppingCartItem shoppingCartItem) {
        SqlParameterSource shoppingCartItemParams = new MapSqlParameterSource()
                .addValue("userId", shoppingCartItem.getUserId())
                .addValue("goodsId", shoppingCartItem.getGoodsId());
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

    private static ShoppingCartItem mapRow(ResultSet rs, int rowNum) throws SQLException {
        return ShoppingCartItem
                .builder()
                .userId(rs.getLong("user_id"))
                .goodsId(rs.getLong("goods_id"))
                .quantity(rs.getInt("quantity"))
                .addingTime(rs.getLong("adding_time"))
                .build();
    }
}
