package test.com.epam.esm.core.service.impl;

import com.epam.esm.core.service.EntityValidatorService;
import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.entity.GiftTag;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import test.com.epam.esm.core.conf.ServiceConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
class EntityValidatorServiceImplTest {
    @Autowired
    private EntityValidatorService service;

    @Test
    void validateCertificate_correctEntity_exceptionDoesNotThrow(@Autowired GiftCertificate giftCertificate) {
        Assertions.assertDoesNotThrow(() -> service.validateCertificate(giftCertificate));
    }

    @Test
    void validateCertificate_invalidName_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setName(null);
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("name"));
    }

    @Test
    void validateCertificate_invalidPrice_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setPrice(0);
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("price"));
    }

    @Test
    void validateCertificate_invalidDuration_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setDuration(0);
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("duration"));
    }

    @Test
    void validateCertificate_emptyTagList_exception(@Autowired GiftCertificate giftCertificate) {
        for (GiftTag giftTag : giftCertificate.getTagList()) {
            giftCertificate.getTagList().remove(giftTag);
        }
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("tagList"));
    }

    @Test
    void validateCertificate_invalidNameAndPrice_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setName(null);
        giftCertificate.setPrice(0);
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("name")
                && exception.getMessage().contains("price"));
    }

    @Test
    void validateCertificate_invalidNameAndDuration_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setName(null);
        giftCertificate.setDuration(0);
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("name")
                && exception.getMessage().contains("duration"));
    }

    @Test
    void validateCertificate_invalidNameAndTagList_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setName(null);
        for (GiftTag giftTag : giftCertificate.getTagList()) {
            giftCertificate.getTagList().remove(giftTag);
        }
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("name")
                && exception.getMessage().contains("tagList"));
    }

    @Test
    void validateCertificate_invalidNameAndPriceAndDuration_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setName(null);
        giftCertificate.setPrice(0);
        giftCertificate.setDuration(0);
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("name")
                && exception.getMessage().contains("price")
                && exception.getMessage().contains("duration"));
    }

    @Test
    void validateCertificate_invalidNameAndPriceAndTagList_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setName(null);
        giftCertificate.setPrice(0);
        for (GiftTag giftTag : giftCertificate.getTagList()) {
            giftCertificate.getTagList().remove(giftTag);
        }
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("name")
                && exception.getMessage().contains("price")
                && exception.getMessage().contains("tagList"));
    }

    @Test
    void validateCertificate_invalidNameAndDurationAndTagList_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setName(null);
        giftCertificate.setDuration(0);
        for (GiftTag giftTag : giftCertificate.getTagList()) {
            giftCertificate.getTagList().remove(giftTag);
        }
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("name")
                && exception.getMessage().contains("duration")
                && exception.getMessage().contains("tagList"));
    }

    @Test
    void validateCertificate_invalidNameAndPriceAndDurationAndTagList_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setName(null);
        giftCertificate.setPrice(0);
        giftCertificate.setDuration(0);
        for (GiftTag giftTag : giftCertificate.getTagList()) {
            giftCertificate.getTagList().remove(giftTag);
        }
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("name")
                && exception.getMessage().contains("price")
                && exception.getMessage().contains("duration")
                && exception.getMessage().contains("tagList"));
    }

    @Test
    void validateCertificate_invalidPriceAndDuration_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setPrice(0);
        giftCertificate.setDuration(0);
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("price")
                && exception.getMessage().contains("duration"));
    }

    @Test
    void validateCertificate_invalidPriceAndTagList_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setPrice(0);
        for (GiftTag giftTag : giftCertificate.getTagList()) {
            giftCertificate.getTagList().remove(giftTag);
        }
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("price")
                && exception.getMessage().contains("tagList"));
    }

    @Test
    void validateCertificate_invalidPriceAndDurationAndTagList_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setPrice(0);
        giftCertificate.setDuration(0);
        for (GiftTag giftTag : giftCertificate.getTagList()) {
            giftCertificate.getTagList().remove(giftTag);
        }
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("price")
                && exception.getMessage().contains("duration")
                && exception.getMessage().contains("tagList"));
    }

    @Test
    void validateCertificate_invalidDurationAndTagList_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setDuration(0);
        for (GiftTag giftTag : giftCertificate.getTagList()) {
            giftCertificate.getTagList().remove(giftTag);
        }
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("duration")
                && exception.getMessage().contains("tagList"));
    }

    @Test
    void validateCertificate_invalidTag_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setDuration(0);
        for (GiftTag giftTag : giftCertificate.getTagList()) {
            giftTag.setName(null);
        }
        Exception exception = Assertions.assertThrows(Exception.class,
                () -> service.validateCertificate(giftCertificate));
        exception.printStackTrace();
        Assertions.assertTrue(exception.getMessage().contains("Certificate")
                && exception.getMessage().contains("Tag"));
    }

    @Test
    void validateTag_correctEntity_exceptionDoesNotThrow(@Autowired GiftTag giftTag) {
        Assertions.assertDoesNotThrow(() -> service.validateTag(giftTag));
    }

    @Test
    void validateTag_invalidName_exception(@Autowired GiftTag giftTag) {
        giftTag.setName(null);
        Exception exception = Assertions.assertThrows(Exception.class, () -> service.validateTag(giftTag));
        Assertions.assertTrue(exception.getMessage().contains("name"));
    }
}
