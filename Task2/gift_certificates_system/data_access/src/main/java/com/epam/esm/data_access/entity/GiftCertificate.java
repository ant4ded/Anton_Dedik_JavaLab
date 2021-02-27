package com.epam.esm.data_access.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GiftCertificate {
    private long id;
    private String name;
    private String description;
    private double price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
}
