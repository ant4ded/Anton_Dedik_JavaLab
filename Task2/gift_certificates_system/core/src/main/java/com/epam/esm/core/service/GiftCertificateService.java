package com.epam.esm.core.service;

import com.epam.esm.data_access.entity.GiftCertificate;

import java.util.Optional;

public interface GiftCertificateService {
    Optional<GiftCertificate> findByName(String name);

    boolean save(GiftCertificate giftCertificate) throws InvalidEntityFieldException;

    boolean update(GiftCertificate giftCertificate);

    boolean delete(GiftCertificate giftCertificate);
}
