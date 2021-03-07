package com.epam.esm.core.controller.assembler;

import com.epam.esm.core.controller.GiftCertificateController;
import com.epam.esm.data_access.entity.GiftCertificate;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GiftCertificateModelAssembler implements
        RepresentationModelAssembler<GiftCertificate, EntityModel<GiftCertificate>> {
    @Override
    public EntityModel<GiftCertificate> toModel(GiftCertificate entity) {
        return EntityModel.of(entity, linkTo(methodOn(GiftCertificateController.class)
                .getOne(entity.getName())).withSelfRel());
    }
}
