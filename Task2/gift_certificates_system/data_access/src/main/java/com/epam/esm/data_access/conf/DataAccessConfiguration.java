package com.epam.esm.data_access.conf;

import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.entity.GiftTag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/gift_certificates");
        dataSource.setUsername("postgresql");
        dataSource.setPassword("root");
        return dataSource;
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
}
