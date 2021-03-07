package com.epam.esm.core.service.impl;

import com.epam.esm.core.service.*;
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
    public GiftCertificate save(GiftCertificate giftCertificate) throws InvalidEntityFieldException,
            DuplicateEntityException, ServiceException {
        if (giftCertificate == null) {
            throw new ServiceException("Received entity was null.");
        }
        if (repository.findByName(giftCertificate.getName()) != null) {
            throw new DuplicateEntityException("Entity with name: " + giftCertificate.getName() + " already exists.");
        }
        validatorService.validateCertificate(giftCertificate);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        if (repository.save(giftCertificate) < 0) {
            throw new ServiceException("Something wrong with saving certificate.");
        }
        giftCertificate.setId(0);
        return giftCertificate;
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) throws ServiceException {
        if (giftCertificate == null) {
            throw new ServiceException("Received entity was null.");
        }
        giftCertificate.setCreateDate(null);
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        repository.updateByName(giftCertificate);
        return findByName(giftCertificate.getName()).orElseThrow(() -> new ServiceException("Nothing by this name."));
    }

    @Override
    public boolean delete(String name) {
        return repository.deleteById(findByName(name).orElse(new GiftCertificate()).getId());
    }

    @Override
    public List<GiftCertificate> findAllByTagName(String name) {
        return repository.findAllByTagName(name);
    }

    @Override
    public List<GiftCertificate> findAllByPartOfCertificateName(String part) {
        return repository.findAllByPartOfCertificateName(part);
    }

    @Override
    public List<GiftCertificate> findAllByPartOfCertificateDescription(String part) {
        return repository.findAllByPartOfCertificateDescription(part);
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
            case CREATE_DATE_NAME_DESC: {
                comparator = Comparator.comparing(GiftCertificate::getCreateDate).thenComparing(GiftCertificate::getName).reversed();
                break;
            }
            default:
                comparator = Comparator.comparing(GiftCertificate::getId);
        }
        list.sort(comparator);
    }
}
