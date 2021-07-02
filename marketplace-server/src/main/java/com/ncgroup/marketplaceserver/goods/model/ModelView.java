package com.ncgroup.marketplaceserver.goods.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor

public class ModelView {
    @JsonProperty("current")
    private int page;
    @JsonProperty("total")
    private int pageTotal;
    @JsonProperty("result_set")
    private List<Good> result;
}
