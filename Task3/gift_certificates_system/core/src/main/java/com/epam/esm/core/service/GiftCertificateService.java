package com.epam.esm.core.service;

import com.epam.esm.data_access.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService {
    Optional<GiftCertificate> findByName(String name);

    GiftCertificate save(GiftCertificate giftCertificate) throws InvalidEntityFieldException, DuplicateEntityException, ServiceException;

    GiftCertificate update(GiftCertificate giftCertificate) throws ServiceException;

    boolean delete(String name);

    List<GiftCertificate> findAllByTagName(String name);

    List<GiftCertificate> findAllByPartOfCertificateName(String part);

    List<GiftCertificate> findAllByPartOfCertificateDescription(String part);

    void sortBy(List<GiftCertificate> list, GiftCertificateSortType sortType);
}
