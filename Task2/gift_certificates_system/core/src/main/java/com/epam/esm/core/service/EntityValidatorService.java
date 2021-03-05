package com.epam.esm.core.service;

import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.entity.GiftTag;

public interface EntityValidatorService {
    void validateCertificate(GiftCertificate giftCertificate) throws InvalidEntityFieldException;

    void validateTag(GiftTag giftTag) throws InvalidEntityFieldException;
}
