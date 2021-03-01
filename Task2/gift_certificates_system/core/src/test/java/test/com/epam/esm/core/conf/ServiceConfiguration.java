package test.com.epam.esm.core.conf;

import com.epam.esm.core.service.EntityValidatorService;
import com.epam.esm.core.service.GiftCertificateService;
import com.epam.esm.core.service.GiftTagService;
import com.epam.esm.core.service.impl.EntityValidatorServiceImpl;
import com.epam.esm.core.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.core.service.impl.GiftTagServiceImpl;
import com.epam.esm.data_access.repository.GiftCertificateRepository;
import com.epam.esm.data_access.repository.GiftTagRepository;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan(basePackages = "test.com.epam.esm.core")
public class ServiceConfiguration {
    @Mock
    private GiftTagRepository giftTagRepository;
    @Mock
    private GiftCertificateRepository giftCertificateRepository;

    @PostConstruct
    private void init(){
        MockitoAnnotations.openMocks(this);
    }

    @Bean
    protected EntityValidatorService entityValidatorService(){
        return new EntityValidatorServiceImpl();
    }

    @Bean
    @Autowired
    protected GiftCertificateService giftCertificateService(EntityValidatorService entityValidatorService){
        return new GiftCertificateServiceImpl(giftCertificateRepository, entityValidatorService);
    }

    @Bean
    @Autowired
    protected GiftTagService giftTagService(EntityValidatorService entityValidatorService){
        return new GiftTagServiceImpl(giftTagRepository, entityValidatorService);
    }
}
