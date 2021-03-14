package com.epam.esm.data_access.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "id")
public class GiftTag {
    private long id;
    private String name;
}
