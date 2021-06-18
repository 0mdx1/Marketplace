package com.ncgroup.marketplaceserver.goods.model;

import com.ncgroup.marketplaceserver.file.service.MediaService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Good {

//    static final String EUROPEAN_DATE_PATTERN = "dd.MM.yyyy";
//    static final DateTimeFormatter EUROPEAN_DATE_FORMATTER = DateTimeFormatter.ofPattern(EUROPEAN_DATE_PATTERN);

    private long id;
    private String goodName;
    private String firmName;
    private int quantity;
    private double price;

    private Unit unit;

    private double discount;

    //private LocalDate shippingDate;

    private boolean inStock;
    private String description;
    private String categoryName;
    private String image;
    private boolean status;

//    public void setShippingDate(LocalDate date) {
//        System.out.println(date);
//        String dat = String.valueOf(date);
//        this.shippingDate = LocalDate.parse(dat, EUROPEAN_DATE_FORMATTER);
//    }

//    public void setPrice(double price, double discount) {
//        this.price = price - (price * (discount / 100));
//    }

//    , MediaService mediaService
    public void setProperties(GoodDto goodDto, Long id) {
        this.setId(id);
       //this.setShippingDate(goodDto.getShippingDate());
        this.setUnit(goodDto.getUnit());
        this.setGoodName(goodDto.getGoodName().toLowerCase());
        this.setFirmName(goodDto.getFirmName().toLowerCase());
        this.setQuantity(goodDto.getQuantity());
//        this.setPrice(goodDto.getPrice(), goodDto.getDiscount());
        this.setPrice(goodDto.getPrice());
        this.setDiscount(goodDto.getDiscount());
        this.setInStock(goodDto.isInStock());
        this.setDescription(goodDto.getDescription());
        this.setCategoryName(goodDto.getCategoryName().toLowerCase());
        this.setImage(goodDto.getImage());
        this.setStatus(goodDto.isStatus());
    }

}
