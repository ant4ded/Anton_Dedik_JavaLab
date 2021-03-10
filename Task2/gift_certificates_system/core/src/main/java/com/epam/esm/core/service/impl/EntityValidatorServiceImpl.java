package com.epam.esm.core.service.impl;

import com.epam.esm.core.controller.advice.util.ExceptionMessagePropertyKey;
import com.epam.esm.core.service.EntityValidatorService;
import com.epam.esm.core.service.InvalidEntityFieldException;
import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.entity.GiftTag;
import com.epam.esm.data_access.entity.table.GiftCertificateTableColumnName;
import com.epam.esm.data_access.entity.table.TagTableColumnName;
import org.springframework.stereotype.Service;

@Service
public class EntityValidatorServiceImpl implements EntityValidatorService {
    private static final String GIFT_CERTIFICATE_FIELD_TAG_LIST = "tagList";
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final String COMMA_SPACE = ", ";
    private static final String DOT = ".";
    private static final String COLON = ":";

    public void validateCertificate(GiftCertificate giftCertificate) throws InvalidEntityFieldException {
        boolean isValidName = giftCertificate.getName() != null && !giftCertificate.getName().equals(EMPTY);
        boolean isValidPrice = giftCertificate.getPrice() > 0;
        boolean isValidDuration = giftCertificate.getDuration() > 0;
        if (!isValidName || !isValidPrice || !isValidDuration || giftCertificate.getTagList().isEmpty()) {
            StringBuilder stringBuilder =
                    new StringBuilder(ExceptionMessagePropertyKey.INVALID_ENTITY_FIELD + SPACE);
            stringBuilder.append(GiftCertificate.class.getSimpleName()).append(COLON)
                    .append(!isValidName ?
                            SPACE + GiftCertificateTableColumnName.COLUMN_NAME : EMPTY)
                    .append(!isValidName && !isValidPrice ?
                            COMMA_SPACE + GiftCertificateTableColumnName.COLUMN_PRICE : EMPTY)
                    .append(isValidName && !isValidPrice ?
                            SPACE + GiftCertificateTableColumnName.COLUMN_PRICE : EMPTY)
                    .append(isValidName && isValidPrice && !isValidDuration ?
                            SPACE + GiftCertificateTableColumnName.COLUMN_DURATION : EMPTY)
                    .append((!isValidName && !isValidDuration) || (!isValidPrice && !isValidDuration) ?
                            COMMA_SPACE + GiftCertificateTableColumnName.COLUMN_DURATION : EMPTY)
                    .append(isValidName && isValidPrice && isValidDuration && giftCertificate.getTagList().isEmpty() ?
                            SPACE + GIFT_CERTIFICATE_FIELD_TAG_LIST : EMPTY)
                    .append((!isValidName && giftCertificate.getTagList().isEmpty()) ||
                            (!isValidPrice && giftCertificate.getTagList().isEmpty()) ||
                            (!isValidDuration && giftCertificate.getTagList().isEmpty()) ?
                            COMMA_SPACE + GIFT_CERTIFICATE_FIELD_TAG_LIST : EMPTY)
                    .append(DOT);
            for (GiftTag giftTag : giftCertificate.getTagList()) {
                try {
                    validateTag(giftTag);
                } catch (InvalidEntityFieldException e) {
                    stringBuilder.append(SPACE);
                    stringBuilder.append(e.getMessage());
                }
            }
            throw new InvalidEntityFieldException(stringBuilder.toString());
        }
    }

    public void validateTag(GiftTag giftTag) throws InvalidEntityFieldException {
        boolean isValidName = giftTag.getName() != null && !giftTag.getName().equals(EMPTY);
        if (!isValidName) {
            StringBuilder stringBuilder =
                    new StringBuilder(ExceptionMessagePropertyKey.INVALID_ENTITY_FIELD + SPACE);
            stringBuilder.append(GiftTag.class.getSimpleName())
                    .append(COLON + SPACE + TagTableColumnName.COLUMN_NAME + DOT);
            throw new InvalidEntityFieldException(stringBuilder.toString());
        }
    }
}
