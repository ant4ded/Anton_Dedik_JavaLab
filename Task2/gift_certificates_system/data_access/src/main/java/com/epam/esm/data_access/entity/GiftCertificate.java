package com.epam.esm.data_access.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GiftCertificate {
    private long id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    @Setter(AccessLevel.NONE)
    private List<GiftTag> tagList;

    public void addTag(GiftTag giftTag){
        tagList.add(giftTag);
    }
}
