package com.epam.esm.data_access.conf;

import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.entity.GiftTag;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.epam.esm.data_access")
public class DataAccessConfiguration {
    private final RowMapper<GiftCertificate> giftCertificateRowMapper =
            BeanPropertyRowMapper.newInstance(GiftCertificate.class);
    private final RowMapper<GiftTag> tagRowMapper =
            BeanPropertyRowMapper.newInstance(GiftTag.class);

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(new HikariConfig("classpath:db.properties"));
    }

    @Bean
    protected ResultSetExtractor<GiftCertificate> giftCertificateResultSetExtractor() {
        return rs -> {
            int rowNum = 0;
            if (rs.next()) {
                GiftCertificate certificate = giftCertificateRowMapper.mapRow(rs, rowNum);
                if (certificate != null) {
                    do {
                        certificate.addTag(tagRowMapper.mapRow(rs, rowNum));
                    } while (rs.next());
                }
                return certificate;
            }
            return null;
        };
    }

    @Bean
    protected ResultSetExtractor<GiftTag> giftTagResultSetExtractor() {
        return rs -> {
            if (rs.next()) {
                return tagRowMapper.mapRow(rs, 0);
            }
            return null;
        };
    }
}
