package com.epam.esm.data_access.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = "id")
public class GiftCertificate {
    private long id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;

    @Setter(AccessLevel.NONE)
    private List<GiftTag> tagList = new LinkedList<>();

    public void addTag(GiftTag giftTag){
        tagList.add(giftTag);
    }
}
