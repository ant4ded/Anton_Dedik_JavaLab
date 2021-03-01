package test.com.epam.esm.data_access.repository.impl;

import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.entity.GiftTag;
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
    void findById_existentId_giftCertificateWithAllTags() {
        GiftCertificate giftCertificate = repository.findById(3);
        Assertions.assertTrue(giftCertificate != null && giftCertificate.getTagList().size() == 3);
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
    void findByName_existentName_giftCertificateWithAllTags() {
        GiftCertificate giftCertificate = repository.findByName("3certificate");
        Assertions.assertTrue(giftCertificate != null && giftCertificate.getTagList().size() == 3);
    }

    @Test
    void findByName_nonExistentName_null() {
        Assertions.assertNull(repository.findByName("999"));
    }

    @Test
    void save_correctEntity_true(@Autowired GiftCertificate giftCertificate) {
        Assertions.assertTrue(repository.save(giftCertificate) > 0);
    }

    @Test
    void save_withExistentTag_true(@Autowired GiftCertificate giftCertificate, @Autowired GiftTag giftTag) {
        giftTag.setName("1tag");
        giftCertificate.addTag(giftTag);
        Assertions.assertTrue(repository.save(giftCertificate) > 0);
    }

    @Test
    void save_withIdentityTags_true(@Autowired GiftCertificate giftCertificate, @Autowired GiftTag giftTag) {
        giftCertificate.addTag(giftTag);
        for (GiftTag tag:giftCertificate.getTagList()){
            tag.setName("1tag");
        }
        Assertions.assertTrue(repository.save(giftCertificate) > 0);
    }

    @Test
    void save_emptyEntity_exception() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> repository.save(new GiftCertificate()));
        Assertions.assertTrue(exception.getMessage().contains("NULL"));
    }

    @Test
    void updateByName_existentEntity_true(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setName("3certificate");
        Assertions.assertTrue(repository.updateByName(giftCertificate));
    }

    @Test
    void updateByName_nonExistentName_false(@Autowired GiftCertificate giftCertificate) {
        Assertions.assertFalse(repository.updateByName(giftCertificate));
    }

    @Test
    void updateByName_emptyEntity_false() {
        GiftCertificate giftCertificate = new GiftCertificate();
        Assertions.assertFalse(repository.updateByName(giftCertificate));
    }

    @Test
    void updateByName_withIdentityTags_true(@Autowired GiftCertificate giftCertificate, @Autowired GiftTag giftTag) {
        giftCertificate.setName("3certificate");
        giftCertificate.addTag(giftTag);
        for (GiftTag tag:giftCertificate.getTagList()){
            tag.setName("1tag");
        }
        Assertions.assertTrue(repository.updateByName(giftCertificate));
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
