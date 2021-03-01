package com.epam.esm.core.service.impl;

import com.epam.esm.core.service.EntityValidatorService;
import com.epam.esm.core.service.GiftCertificateService;
import com.epam.esm.core.service.InvalidEntityFieldException;
import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepository repository;
    private final EntityValidatorService validatorService;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateRepository repository, EntityValidatorService validatorService) {
        this.repository = repository;
        this.validatorService = validatorService;
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        return Optional.ofNullable(repository.findByName(name));
    }

    @Override
    public boolean save(GiftCertificate giftCertificate) throws InvalidEntityFieldException {
        validatorService.validateCertificate(giftCertificate);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        repository.save(giftCertificate);
        return repository.save(giftCertificate) > 0;
    }

    @Override
    public boolean update(GiftCertificate giftCertificate) {
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        return repository.updateByName(giftCertificate);
    }

    @Override
    public boolean delete(GiftCertificate giftCertificate) {
        return repository.deleteById(findByName(giftCertificate.getName()).orElse(new GiftCertificate()).getId());
    }


}
