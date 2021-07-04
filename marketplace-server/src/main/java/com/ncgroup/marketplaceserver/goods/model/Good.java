package com.ncgroup.marketplaceserver.goods.model;

import java.time.OffsetDateTime;

import com.ncgroup.marketplaceserver.file.service.MediaService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Good {

    private long id;
    private String goodName;
    private String firmName;
    private double quantity;
    private double price;

    private Unit unit;

    private double discount;

    private OffsetDateTime shippingDate;

    private boolean inStock;
    private String description;
    private String categoryName;
    private String image;
    private boolean status;


    public Good(GoodDto goodDto, Long id, MediaService mediaService) {
        this.setStatus(true);
        this.setId(id);
        this.setShippingDate(goodDto.getShippingDate());
        this.setUnit(goodDto.getUnit());
        this.setGoodName(goodDto.getGoodName().toLowerCase());
        this.setFirmName(goodDto.getFirmName().toLowerCase());
        this.setQuantity(goodDto.getQuantity());
        this.setPrice(goodDto.getPrice());
        this.setDiscount(goodDto.getDiscount());
        this.setInStock(goodDto.isInStock());
        this.setDescription(goodDto.getDescription());
        this.setCategoryName(goodDto.getCategoryName().toLowerCase());
        this.setImage(goodDto.getImage(), mediaService);
    }

    public void setImage(String image, MediaService mediaService) {
        this.image = mediaService.getCloudStorage().getResourceUrl(image);
    }

    public void setProperties(GoodDto goodDto, Long id, MediaService mediaService) {
        this.setId(id);
        this.setShippingDate(goodDto.getShippingDate());
        this.setUnit(goodDto.getUnit());
        this.setGoodName(goodDto.getGoodName().toLowerCase());
        this.setFirmName(goodDto.getFirmName().toLowerCase());
        this.setQuantity(goodDto.getQuantity());
        this.setPrice(goodDto.getPrice());
        this.setDiscount(goodDto.getDiscount());
        this.setInStock(goodDto.isInStock());
        this.setDescription(goodDto.getDescription());
        this.setCategoryName(goodDto.getCategoryName().toLowerCase());
        this.setImage(goodDto.getImage(), mediaService);
        this.setStatus(goodDto.isStatus());
    }

}
