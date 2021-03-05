package com.epam.esm.core.service.impl;

import com.epam.esm.core.service.EntityValidatorService;
import com.epam.esm.core.service.GiftCertificateService;
import com.epam.esm.core.service.GiftCertificateSortType;
import com.epam.esm.core.service.InvalidEntityFieldException;
import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
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
        if (giftCertificate == null) {
            return false;
        }
        validatorService.validateCertificate(giftCertificate);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        return repository.save(giftCertificate) > 0;
    }

    @Override
    public boolean update(GiftCertificate giftCertificate) {
        if (giftCertificate == null) {
            return false;
        }
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        return repository.updateByName(giftCertificate);
    }

    @Override
    public boolean delete(GiftCertificate giftCertificate) {
        if (giftCertificate == null) {
            return false;
        }
        return repository.deleteById(findByName(giftCertificate.getName()).orElse(new GiftCertificate()).getId());
    }

    @Override
    public void sortBy(List<GiftCertificate> list, GiftCertificateSortType sortType) {
        Comparator<GiftCertificate> comparator;
        switch (sortType) {
            case NAME_ASC: {
                comparator = Comparator.comparing(GiftCertificate::getName);
                break;
            }
            case NAME_DESC: {
                comparator = Comparator.comparing(GiftCertificate::getName).reversed();
                break;
            }
            case CREATE_DATE_ASC: {
                comparator = Comparator.comparing(GiftCertificate::getCreateDate);
                break;
            }
            case CREATE_DATE_DESC: {
                comparator = Comparator.comparing(GiftCertificate::getCreateDate).reversed();
                break;
            }
            case NAME_CREATE_DATE_ASC: {
                comparator = Comparator.comparing(GiftCertificate::getName).thenComparing(GiftCertificate::getCreateDate);
                break;
            }
            case NAME_CREATE_DATE_DESC: {
                comparator = Comparator.comparing(GiftCertificate::getName).thenComparing(GiftCertificate::getCreateDate).reversed();
                break;
            }
            case CREATE_DATE_NAME_ASC: {
                comparator = Comparator.comparing(GiftCertificate::getCreateDate).thenComparing(GiftCertificate::getName);
                break;
            }
            case CREATE_DATE_NAME_DESC:{
                comparator = Comparator.comparing(GiftCertificate::getCreateDate).thenComparing(GiftCertificate::getName).reversed();
                break;
            }
            default:
                comparator = Comparator.comparing(GiftCertificate::getId);
        }
        list.sort(comparator);
    }
}
