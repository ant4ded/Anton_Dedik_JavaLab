package com.epam.esm.core.controller;

import com.epam.esm.core.controller.assembler.GiftCertificateModelAssembler;
import com.epam.esm.core.service.DuplicateEntityException;
import com.epam.esm.core.service.GiftCertificateService;
import com.epam.esm.core.service.InvalidEntityFieldException;
import com.epam.esm.core.service.ServiceException;
import com.epam.esm.data_access.entity.GiftCertificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/certificates")
public class GiftCertificateController {
    private final GiftCertificateService service;
    private final GiftCertificateModelAssembler assembler;

    @Autowired
    public GiftCertificateController(GiftCertificateService service, GiftCertificateModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping(value = "/{name}")
    public EntityModel<GiftCertificate> getOne(@PathVariable String name) {
        GiftCertificate certificate = service.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Nothing by this name: " + name));
        return assembler.toModel(certificate);
    }

    @GetMapping("/allByName")
    public CollectionModel<EntityModel<GiftCertificate>> getAllByName(@RequestParam String part) {
        List<EntityModel<GiftCertificate>> list = service.findAllByPartOfCertificateName(part).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(list, linkTo(methodOn(GiftCertificateController.class)
                .getAllByName(part)).withSelfRel());
    }

    @GetMapping("/allByDescription")
    public CollectionModel<EntityModel<GiftCertificate>> getAllByDescription(@RequestParam String part) {
        List<EntityModel<GiftCertificate>> list = service.findAllByPartOfCertificateDescription(part).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(list, linkTo(methodOn(GiftCertificateController.class)
                .getAllByDescription(part)).withSelfRel());
    }

    @GetMapping("/allByTagName")
    public CollectionModel<EntityModel<GiftCertificate>> getAllByTagName(@RequestParam String name) {
        List<EntityModel<GiftCertificate>> list = service.findAllByTagName(name).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(list, linkTo(methodOn(GiftCertificateController.class)
                .getAllByTagName(name)).withSelfRel());
    }

    @PostMapping
    public ResponseEntity<EntityModel<GiftCertificate>> create(@RequestBody GiftCertificate certificate)
            throws InvalidEntityFieldException, DuplicateEntityException, ServiceException {
        EntityModel<GiftCertificate> model = assembler.toModel(service.save(certificate));
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    @PutMapping(value = "/{name}")
    public ResponseEntity<EntityModel<GiftCertificate>> update(@RequestBody GiftCertificate certificate)
            throws ServiceException {
        EntityModel<GiftCertificate> model = assembler.toModel(service.update(certificate));
        return ResponseEntity.created(model.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(model);
    }

    @DeleteMapping(value = "/{name}")
    public ResponseEntity<EntityModel<GiftCertificate>> delete(@PathVariable String name) {
        service.delete(name);
        return ResponseEntity.noContent().build();
    }
}
