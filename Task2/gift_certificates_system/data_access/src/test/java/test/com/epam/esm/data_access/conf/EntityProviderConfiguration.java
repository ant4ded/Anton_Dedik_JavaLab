package test.com.epam.esm.data_access.conf;

import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.entity.GiftTag;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;

@Configuration
public class EntityProviderConfiguration {
    private int prototypeCount = 100;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    protected GiftCertificate giftCertificate() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(prototypeCount);
        giftCertificate.setName(prototypeCount + "certificate");
        giftCertificate.setDescription(prototypeCount + "description");
        giftCertificate.setPrice(prototypeCount);
        giftCertificate.setDuration(prototypeCount);
        giftCertificate.setCreateDate(LocalDateTime.now());
        giftCertificate.setLastUpdateDate(LocalDateTime.now());
        prototypeCount++;
        return giftCertificate;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    protected GiftTag giftTag() {
        GiftTag giftTag = new GiftTag();
        giftTag.setId(prototypeCount);
        giftTag.setName(prototypeCount + "tag");
        prototypeCount++;
        return giftTag;
    }
}
