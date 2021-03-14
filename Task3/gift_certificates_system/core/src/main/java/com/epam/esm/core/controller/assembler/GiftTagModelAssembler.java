package com.epam.esm.core.controller.assembler;

import com.epam.esm.core.controller.GiftTagController;
import com.epam.esm.data_access.entity.GiftTag;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftTagModelAssembler implements RepresentationModelAssembler<GiftTag, EntityModel<GiftTag>> {
    @Override
    public EntityModel<GiftTag> toModel(GiftTag entity) {
        return EntityModel.of(entity, linkTo(methodOn(GiftTagController.class)
                .getOne(entity.getName())).withSelfRel());
    }
}
