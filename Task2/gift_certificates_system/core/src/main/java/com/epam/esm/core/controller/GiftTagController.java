package com.epam.esm.core.controller;

import com.epam.esm.core.service.GiftTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class GiftTagController {
    private final GiftTagService giftTagService;

    @Autowired
    public GiftTagController(GiftTagService giftTagService) {
        this.giftTagService = giftTagService;
    }
}
