package com.epam.esm.data_access.repository;

import com.epam.esm.data_access.entity.GiftCertificate;

public interface GiftCertificateRepository {
    GiftCertificate findById(long id);

    long save(GiftCertificate giftCertificate);

    boolean updateById(GiftCertificate giftCertificate);

    boolean deleteById(long id);
}
