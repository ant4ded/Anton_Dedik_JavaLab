package com.epam.esm.data_access.repository;

import com.epam.esm.data_access.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateRepository {
    GiftCertificate findById(long id);

    GiftCertificate findByName(String name);

    long save(GiftCertificate giftCertificate);

    boolean updateByName(GiftCertificate giftCertificate);

    boolean deleteById(long id);

    List<GiftCertificate> findAllByTagName(String name);

    List<GiftCertificate> findAllByPartOfCertificateName(String part);

    List<GiftCertificate> findAllByPartOfCertificateDescription(String part);
}
