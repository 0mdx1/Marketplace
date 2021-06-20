package com.ncgroup.marketplaceserver.shopping.cart.validator;

import com.ncgroup.marketplaceserver.goods.model.Good;
import com.ncgroup.marketplaceserver.goods.repository.GoodsRepository;
import com.ncgroup.marketplaceserver.shopping.cart.annotation.SufficientGoodsStock;
import com.ncgroup.marketplaceserver.shopping.cart.model.dto.ShoppingCartItemCreateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
public class SufficientGoodsStockValidator implements ConstraintValidator<SufficientGoodsStock, ShoppingCartItemCreateDto> {

    private GoodsRepository goodsRepository;

    @Autowired
    public SufficientGoodsStockValidator(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @Override
    public void initialize(SufficientGoodsStock constraintAnnotation) {
    }

    @Override
    public boolean isValid(ShoppingCartItemCreateDto item, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        if (item==null){
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Cannot be empty")
                    .addPropertyNode("item")
                    .addConstraintViolation();
            return false;
        }
        Optional<Good> goodsOpt = goodsRepository.findById(item.getGoodsId());
        if(!goodsOpt.isPresent()){
            constraintValidatorContext
                .buildConstraintViolationWithTemplate("Cannot find such product")
                .addPropertyNode("item")
                .addConstraintViolation();
            return false;
        }
        Good goods = goodsOpt.get();
        if(!goods.isStatus()){
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(goods.getGoodName()+" is not available")
                    .addPropertyNode("item")
                    .addConstraintViolation();
            return false;
        }
        if(!goods.isInStock()){
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(goods.getGoodName()+" is out of stock")
                    .addPropertyNode("item")
                    .addConstraintViolation();
            return false;
        }
        if(goods.getQuantity()<item.getQuantity()){
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(goods.getGoodName()+" quantity exceeds current stock of "+goods.getQuantity())
                    .addPropertyNode("item")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
