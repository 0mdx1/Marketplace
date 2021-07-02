package com.ncgroup.marketplaceserver.goods;

import com.ncgroup.marketplaceserver.goods.model.SortCategory;
import org.springframework.core.convert.converter.Converter;

public class StringToEnumConverter implements Converter<String, SortCategory> {
    @Override
    public SortCategory convert(String source) {
        return SortCategory.valueOf(source.toUpperCase());
    }
}
