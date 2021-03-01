package test.com.epam.esm.core.service.impl;

import com.epam.esm.core.service.EntityValidatorService;
import com.epam.esm.core.service.GiftCertificateService;
import com.epam.esm.core.service.InvalidEntityFieldException;
import com.epam.esm.core.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.repository.GiftCertificateRepository;
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
class GiftCertificateServiceImplTest {
    @Mock
    private GiftCertificateRepository repository;
    @Mock
    private EntityValidatorService validator;

    private GiftCertificateService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.service = new GiftCertificateServiceImpl(repository, validator);
    }

    @Test
    void findByName_entityNotExist_optionalEmpty() {
        Mockito.when(repository.findByName(Mockito.anyString())).thenReturn(null);
        Assertions.assertFalse(service.findByName(Mockito.anyString()).isPresent());
    }

    @Test
    void findByName_entityExist_present() {
        Mockito.when(repository.findByName(Mockito.anyString()))
                .thenReturn(new GiftCertificate());
        Assertions.assertTrue(service.findByName(Mockito.anyString()).isPresent());
    }

    @Test
    void save_correctEntity_true(@Autowired GiftCertificate giftCertificate) throws InvalidEntityFieldException {
        Mockito.when(repository.save(giftCertificate)).thenReturn(1L);
        Assertions.assertTrue(service.save(giftCertificate));
    }

    @Test
    void save_incorrectEntity_exception(@Autowired GiftCertificate giftCertificate) throws InvalidEntityFieldException {
        Mockito.doThrow(InvalidEntityFieldException.class).when(validator).validateCertificate(giftCertificate);
        Assertions.assertThrows(InvalidEntityFieldException.class, () -> service.save(giftCertificate));
    }

    @Test
    void save_emptyEntity_exception() throws InvalidEntityFieldException {
        Mockito.doThrow(InvalidEntityFieldException.class).when(validator).validateCertificate(new GiftCertificate());
        Assertions.assertThrows(InvalidEntityFieldException.class, () -> service.save(new GiftCertificate()));
    }

    @Test
    void save_null_false() throws InvalidEntityFieldException {
        Assertions.assertFalse(service.save(null));
    }

    @Test
    void update_correctEntity_true(@Autowired GiftCertificate giftCertificate) {
        Mockito.when(repository.updateByName(giftCertificate)).thenReturn(true);
        Assertions.assertTrue(service.update(giftCertificate));
    }

    @Test
    void update_null_false(){
        Assertions.assertFalse(service.update(null));
    }

    @Test
    void update_nullName_false(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setName(null);
        Mockito.when(repository.updateByName(giftCertificate)).thenReturn(false);
        Assertions.assertFalse(service.update(giftCertificate));
    }

    @Test
    void delete_existEntity_true(@Autowired GiftCertificate giftCertificate) {
        Mockito.when(repository.findByName(giftCertificate.getName())).thenReturn(giftCertificate);
        Assertions.assertFalse(service.delete(giftCertificate));
    }

    @Test
    void delete_null_false(){
        Assertions.assertFalse(service.delete(null));
    }

    @Test
    void delete_nullName_false(@Autowired GiftCertificate giftCertificate) {
        giftCertificate.setName(null);
        Mockito.when(repository.findByName(null)).thenReturn(null);
        Assertions.assertFalse(service.delete(giftCertificate));
    }
}
