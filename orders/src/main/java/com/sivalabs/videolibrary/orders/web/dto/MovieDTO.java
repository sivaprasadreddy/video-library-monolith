package com.sivalabs.videolibrary.orders.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class MovieDTO {
    private String title;
    private Long tmdbId;
    private BigDecimal price;
}
