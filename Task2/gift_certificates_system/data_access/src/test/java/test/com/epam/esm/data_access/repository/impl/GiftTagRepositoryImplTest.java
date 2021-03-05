package test.com.epam.esm.data_access.repository.impl;


import com.epam.esm.data_access.entity.GiftTag;
import com.epam.esm.data_access.repository.GiftTagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import test.com.epam.esm.data_access.conf.DataAccessConfiguration;

@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ContextConfiguration(classes = DataAccessConfiguration.class)
class GiftTagRepositoryImplTest {
    @Autowired
    private GiftTagRepository repository;

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
    void findByName_existentName_notNull() {
        Assertions.assertNotNull(repository.findByName("1tag"));
    }

    @Test
    void findByName_nonExistentName_null() {
        Assertions.assertNull(repository.findByName("999"));
    }

    @Test
    void save_correctEntity_true(@Autowired GiftTag giftTag) {
        Assertions.assertTrue(repository.save(giftTag) > 0);
    }

    @Test
    void save_emptyEntity_exception() {
        Exception exception = Assertions.assertThrows(Exception.class, () -> repository.save(new GiftTag()));
        Assertions.assertTrue(exception.getMessage().contains("NULL"));
    }

    @Test
    void save_existentName_exception() {
        GiftTag giftTag = new GiftTag();
        giftTag.setName("1tag");
        Exception exception = Assertions.assertThrows(Exception.class, () -> repository.save(giftTag));
        Assertions.assertTrue(exception.getMessage().contains("Unique"));
    }

    @Test
    void deleteById_existentEntity_true() {
        Assertions.assertTrue(repository.deleteById(2));
    }

    @Test
    void deleteById_nonExistentId_false() {
        Assertions.assertFalse(repository.deleteById(-1));
    }
}
