package test.com.epam.esm.data_access.repository.impl;

import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.repository.GiftCertificateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import test.com.epam.esm.data_access.conf.DataAccessConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DataAccessConfiguration.class)
class GiftCertificateRepositoryImplTest {
    @Autowired
    private GiftCertificateRepository repository;

    @Test
    void findById_existentId_notNull() {
        Assertions.assertNotNull(repository.findById(1));
    }

    @Test
    void findById_nonExistentId_null() {
        Assertions.assertNull(repository.findById(999));
    }

    @Test
    void findById_incorrectId_null() {
        Assertions.assertNull(repository.findById(-1));
    }

    @Test
    void save_correctEntity_true(@Autowired GiftCertificate giftCertificate) {
        Assertions.assertTrue(repository.save(giftCertificate));
    }

    @Test
    void save_existentId_exception(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setId(1);
        Exception exception = Assertions.assertThrows(Exception.class, () -> repository.save(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("primary key"));
    }

    @Test
    void save_emptyEntity_exception() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> repository.save(new GiftCertificate()));
        Assertions.assertTrue(exception.getMessage().contains("NULL"));
    }

    @Test
    void updateById_existentEntity_true(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setId(1);
        Assertions.assertTrue(repository.updateById(giftCertificate));
    }

    @Test
    void updateById_nonExistentId_false(@Autowired GiftCertificate giftCertificate) {
        Assertions.assertFalse(repository.updateById(giftCertificate));
    }

    @Test
    void updateById_emptyEntity_exception() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        Exception exception = Assertions.assertThrows(Exception.class, () -> repository.updateById(giftCertificate));
        Assertions.assertTrue(exception.getMessage().contains("NULL"));
    }

    @Test
    void deleteById_existentEntity_true() {
        Assertions.assertTrue(repository.deleteById(1));
    }

    @Test
    void deleteById_nonExistentId_false() {
        Assertions.assertFalse(repository.deleteById(-1));
    }
}
