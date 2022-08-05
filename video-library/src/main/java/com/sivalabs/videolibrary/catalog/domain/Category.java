package com.sivalabs.videolibrary.catalog.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    private Long id;
    private Long uuid;
    private String name;
    private String slug;
}
