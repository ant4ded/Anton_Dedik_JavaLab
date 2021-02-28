package test.com.epam.esm.data_access.conf;

import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.entity.GiftTag;
import com.epam.esm.data_access.repository.GiftCertificateRepository;
import com.epam.esm.data_access.repository.GiftTagRepository;
import com.epam.esm.data_access.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.data_access.repository.impl.GiftTagRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "test.com.epam.esm.data_access")
public class DataAccessConfiguration {
    private final RowMapper<GiftCertificate> giftCertificateRowMapper =
            BeanPropertyRowMapper.newInstance(GiftCertificate.class);
    private final RowMapper<GiftTag> tagRowMapper =
            BeanPropertyRowMapper.newInstance(GiftTag.class);

    @Bean
    protected DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
    }

    @Bean
    protected ResultSetExtractor<GiftCertificate> giftCertificateRowMapper() {
        return rs -> {
            int rowNum = 0;
            if (rs.next()) {
                GiftCertificate certificate = giftCertificateRowMapper.mapRow(rs, rowNum);
                while (rs.next() && certificate != null) {
                    certificate.addTag(tagRowMapper.mapRow(rs, rowNum++));
                }
                return certificate;
            }
            return null;
        };
    }

    @Bean
    protected ResultSetExtractor<GiftTag> giftTagRowMapper() {
        return rs -> {
            if (rs.next()) {
                return tagRowMapper.mapRow(rs, 0);
            }
            return null;
        };
    }

    @Bean
    @Autowired
    protected GiftCertificateRepository giftCertificateRepository(DataSource dataSource,
                                                                  ResultSetExtractor<GiftCertificate> resultSetExtractor) {
        return new GiftCertificateRepositoryImpl(dataSource, resultSetExtractor);
    }

    @Bean
    @Autowired
    protected GiftTagRepository giftTagRepository(DataSource dataSource,
                                                  ResultSetExtractor<GiftTag> resultSetExtractor) {
        return new GiftTagRepositoryImpl(dataSource, resultSetExtractor);
    }
}
