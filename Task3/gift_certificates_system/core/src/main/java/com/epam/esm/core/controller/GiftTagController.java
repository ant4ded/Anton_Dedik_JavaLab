package com.epam.esm.core.controller;

import com.epam.esm.core.controller.assembler.GiftTagModelAssembler;
import com.epam.esm.core.service.GiftTagService;
import com.epam.esm.data_access.entity.GiftTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tag")
public class GiftTagController {
    private final GiftTagService service;
    private final GiftTagModelAssembler assembler;

    @Autowired
    public GiftTagController(GiftTagService service, GiftTagModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping(value = "/{name}")
    public EntityModel<GiftTag> getOne(@PathVariable String name) {
        GiftTag tag = service.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Nothing by this name: " + name));
        return assembler.toModel(tag);
    }
}
