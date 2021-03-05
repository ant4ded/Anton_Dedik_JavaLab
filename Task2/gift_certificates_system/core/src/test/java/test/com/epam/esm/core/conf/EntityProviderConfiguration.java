package test.com.epam.esm.core.conf;

import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.entity.GiftTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.time.LocalDateTime;
import java.util.*;

@Configuration
public class EntityProviderConfiguration {
    private int prototypeCount = 100;
    private int listCount = 1;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    protected GiftCertificate giftCertificate(@Autowired GiftTag giftTag) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(prototypeCount);
        giftCertificate.setName(prototypeCount + "certificate");
        giftCertificate.setDescription(prototypeCount + "description");
        giftCertificate.setPrice(prototypeCount);
        giftCertificate.setDuration(prototypeCount);
        giftCertificate.setCreateDate(LocalDateTime.parse("2000-01-01T00:00:00.000"));
        giftCertificate.setLastUpdateDate(LocalDateTime.parse("2000-01-01T00:00:00.000"));
        giftCertificate.addTag(giftTag);
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

    @Bean
    protected List<GiftCertificate> giftCertificateList(){
        return Arrays.asList(giftCertificateForList(),
                giftCertificateForList(),
                giftCertificateForList(),
                giftCertificateForList(),
                giftCertificateForList(),
                giftCertificateForList(),
                giftCertificateForList(),
                giftCertificateForList(),
                giftCertificateForList(),
                giftCertificateForList());
    }

    private GiftCertificate giftCertificateForList() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(listCount);
        giftCertificate.setName(listCount + "certificate");
        giftCertificate.setDescription(listCount + "description");
        giftCertificate.setPrice(listCount);
        giftCertificate.setDuration(listCount);
        giftCertificate.setCreateDate(LocalDateTime.parse("2000-01-01T00:00:00.000").plusMonths(listCount));
        giftCertificate.setLastUpdateDate(LocalDateTime.parse("2000-01-01T00:00:00.000").plusMonths(listCount));
        giftCertificate.addTag(giftTagForList());
        return giftCertificate;
    }

    protected GiftTag giftTagForList() {
        GiftTag giftTag = new GiftTag();
        giftTag.setId(listCount);
        giftTag.setName(listCount + "tag");
        listCount++;
        return giftTag;
    }
}
