package com.ncgroup.marketplaceserver.goods;

import com.ncgroup.marketplaceserver.file.service.MediaService;
import com.ncgroup.marketplaceserver.goods.service.GoodsService;

public class MediaDecorator extends Decorator {

    MediaService mediaService;

    MediaDecorator(GoodsService goodsService, MediaService mediaService) {
        super(goodsService);
        this.mediaService = mediaService;
    }


}
