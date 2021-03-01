package com.epam.esm.core.service.impl;

import com.epam.esm.core.service.EntityValidatorService;
import com.epam.esm.core.service.InvalidEntityFieldException;
import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.entity.GiftTag;
import org.springframework.stereotype.Service;

@Service
public class EntityValidatorServiceImpl implements EntityValidatorService {
    public void validateCertificate(GiftCertificate giftCertificate) throws InvalidEntityFieldException {
        boolean isValidName = giftCertificate.getName() != null && !giftCertificate.getName().equals("");
        boolean isValidPrice = giftCertificate.getPrice() > 0;
        boolean isValidDuration = giftCertificate.getDuration() > 0;
        if (!isValidName || !isValidPrice || !isValidDuration || giftCertificate.getTagList().isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder("Invalid entity fields ");
            stringBuilder.append("of ").append(GiftCertificate.class.getSimpleName()).append(':')
                    .append(!isValidName ? " name" : "")
                    .append(!isValidName && !isValidPrice ? ", price" : "")
                    .append(isValidName && !isValidPrice ? " price" : "")
                    .append(isValidName && isValidPrice && !isValidDuration ? " duration" : "")
                    .append((!isValidName && !isValidDuration) || (!isValidPrice && !isValidDuration) ? ", duration" : "")
                    .append(isValidName && isValidPrice && isValidDuration &&
                            giftCertificate.getTagList().isEmpty() ? " tagList" : "")
                    .append((!isValidName && giftCertificate.getTagList().isEmpty()) ||
                            (!isValidPrice && giftCertificate.getTagList().isEmpty()) ||
                            (!isValidDuration && giftCertificate.getTagList().isEmpty()) ? ", tagList" : "")
                    .append('.');
            for (GiftTag giftTag : giftCertificate.getTagList()) {
                try {
                    validateTag(giftTag);
                } catch (InvalidEntityFieldException e) {
                    stringBuilder.append(' ');
                    stringBuilder.append(e.getMessage());
                }
            }
            throw new InvalidEntityFieldException(stringBuilder.toString());
        }
    }

    public void validateTag(GiftTag giftTag) throws InvalidEntityFieldException {
        boolean isValidName = giftTag.getName() != null && !giftTag.getName().equals("");
        if (!isValidName) {
            StringBuilder stringBuilder = new StringBuilder("Invalid entity fields ");
            stringBuilder.append("of ").append(GiftCertificate.class.getSimpleName()).append(": name.");
            throw new InvalidEntityFieldException(stringBuilder.toString());
        }
    }
}
