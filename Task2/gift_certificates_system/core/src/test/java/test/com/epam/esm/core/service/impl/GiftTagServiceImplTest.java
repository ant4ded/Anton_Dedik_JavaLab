package test.com.epam.esm.core.service.impl;

import com.epam.esm.core.service.EntityValidatorService;
import com.epam.esm.core.service.GiftTagService;
import com.epam.esm.core.service.InvalidEntityFieldException;
import com.epam.esm.core.service.impl.GiftTagServiceImpl;
import com.epam.esm.data_access.entity.GiftTag;
import com.epam.esm.data_access.repository.GiftTagRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import test.com.epam.esm.core.conf.ServiceConfiguration;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
class GiftTagServiceImplTest {
    @Mock
    private GiftTagRepository repository;
    @Mock
    private EntityValidatorService validator;

    private GiftTagService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.service = new GiftTagServiceImpl(repository, validator);
    }

    @Test
    void findByName_entityNotExist_optionalEmpty() {
        Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(null);
        Assertions.assertFalse(service.findByName(Mockito.anyString()).isPresent());
    }

    @Test
    void findByName_entityExist_present() {
        Mockito.when(repository.findByName(Mockito.anyString()))
                .thenReturn(new GiftTag());
        Assertions.assertTrue(service.findByName(Mockito.anyString()).isPresent());
    }

    @Test
    void save_correctEntity_true(@Autowired GiftTag giftTag) throws InvalidEntityFieldException {
        Mockito.when(repository.save(giftTag)).thenReturn(1L);
        Assertions.assertTrue(service.save(giftTag));
    }

    @Test
    void save_incorrectEntity_exception(@Autowired GiftTag giftTag) throws InvalidEntityFieldException {
        Mockito.doThrow(InvalidEntityFieldException.class).when(validator).validateTag(giftTag);
        Assertions.assertThrows(InvalidEntityFieldException.class, () -> service.save(giftTag));
    }

    @Test
    void save_emptyEntity_exception() throws InvalidEntityFieldException {
        Mockito.doThrow(InvalidEntityFieldException.class).when(validator).validateTag(new GiftTag());
        Assertions.assertThrows(InvalidEntityFieldException.class, () -> service.save(new GiftTag()));
    }

    @Test
    void save_null_false() throws InvalidEntityFieldException {
        Assertions.assertFalse(service.save(null));
    }

    @Test
    void delete_existEntity_true(@Autowired GiftTag giftTag) {
        Mockito.when(repository.findByName(giftTag.getName())).thenReturn(giftTag);
        Assertions.assertFalse(service.delete(giftTag));
    }

    @Test
    void delete_null_false() {
        Assertions.assertFalse(service.delete(null));
    }

    @Test
    void delete_nullName_false(@Autowired GiftTag giftTag) {
        giftTag.setName(null);
        Mockito.when(repository.findByName(null)).thenReturn(null);
        Assertions.assertFalse(service.delete(giftTag));
    }
}
