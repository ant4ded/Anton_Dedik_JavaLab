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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * @author Anton Dedik
 * @version 0.0.1
 * Controller with public APIs for GiftCertificates entity.
 */
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

    /**
     * Find one GiftCertificate by name in database.
     *
     * @param name name of GiftCertificate.
     * @return GiftCertificate with self link.
     * @throws EntityNotFoundException if service method found nothing.
     */
    @GetMapping(value = "/{name}")
    public EntityModel<GiftCertificate> getOne(@PathVariable String name) {
        GiftCertificate certificate = service.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Nothing by this name: " + name));
        return assembler.toModel(certificate);
    }

    /**
     * Find all GiftCertificate by part of name in database.
     *
     * @param part part of GiftCertificate name.
     * @return GiftCertificate list with self link for each entity.
     */
    @GetMapping("/allByName")
    public CollectionModel<EntityModel<GiftCertificate>> getAllByName(@RequestParam String part) {
        List<EntityModel<GiftCertificate>> list = service.findAllByPartOfCertificateName(part).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(list, linkTo(methodOn(GiftCertificateController.class)
                .getAllByName(part)).withSelfRel());
    }

    /**
     * Find all GiftCertificate by part of description in database.
     *
     * @param part part of GiftCertificate description.
     * @return GiftCertificate list with self link for each entity.
     */
    @GetMapping("/allByDescription")
    public CollectionModel<EntityModel<GiftCertificate>> getAllByDescription(@RequestParam String part) {
        List<EntityModel<GiftCertificate>> list = service.findAllByPartOfCertificateDescription(part).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(list, linkTo(methodOn(GiftCertificateController.class)
                .getAllByDescription(part)).withSelfRel());
    }

    /**
     * Find all GiftCertificate by GiftTag name in database.
     *
     * @param name GiftTag name.
     * @return GiftCertificate list with self link for each entity.
     */
    @GetMapping("/allByTagName")
    public CollectionModel<EntityModel<GiftCertificate>> getAllByTagName(@RequestParam String name) {
        List<EntityModel<GiftCertificate>> list = service.findAllByTagName(name).stream()
                .map(assembler::toModel).collect(Collectors.toList());
        return CollectionModel.of(list, linkTo(methodOn(GiftCertificateController.class)
                .getAllByTagName(name)).withSelfRel());
    }

    /**
     * Create new GiftCertificate with GiftTags in database.
     * If GiftTag not exist in database, it will be created.
     * If GiftTag exist in database, it will be updated.
     *
     * @param certificate GiftCertificate entity.
     * @return GiftCertificate with self link.
     * @throws InvalidEntityFieldException if fields of GiftCertificate invalid.
     * @throws DuplicateEntityException if this entity already exists in database.
     * @throws ServiceException if saving did not happened.
     */
    @PostMapping(value = "/new", consumes = "application/json", produces = "application/json")
    public EntityModel<GiftCertificate> create(@RequestBody GiftCertificate certificate)
            throws InvalidEntityFieldException, DuplicateEntityException, ServiceException {
        return assembler.toModel(service.save(certificate));
    }

    /**
     * Update GiftCertificate with GiftTags in database.
     * If GiftTag not exist in database, it will be created.
     * If GiftTag exist in database, it will be updated.
     *
     * @param certificate GiftCertificate entity.
     * @return GiftCertificate with self link.
     * @throws ServiceException if saving did not happened.
     */
    @PutMapping(value = "/{name}")
    public EntityModel<GiftCertificate> update(@RequestBody GiftCertificate certificate)
            throws ServiceException {
        return assembler.toModel(service.update(certificate));
    }

    /**
     * Delete GiftCertificate by name in database.
     *
     * @param name GiftCertificate entity.
     * @return empty body.
     */
    @DeleteMapping(value = "/{name}")
    public ResponseEntity<EntityModel<GiftCertificate>> delete(@PathVariable String name) {
        service.delete(name);
        return ResponseEntity.noContent().build();
    }
}
