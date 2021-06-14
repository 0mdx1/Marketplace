package com.ncgroup.marketplaceserver.shopping.cart.annotation;

import com.ncgroup.marketplaceserver.shopping.cart.validator.SufficientGoodsStockValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SufficientGoodsStockValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SufficientGoodsStock {

    String message() default "Insufficient goods stock";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
