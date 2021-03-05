package test.com.epam.esm.data_access.conf;

import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.entity.GiftTag;
import com.epam.esm.data_access.entity.table.GiftCertificateTableColumnName;
import com.epam.esm.data_access.entity.table.TagTableColumnName;
import com.epam.esm.data_access.repository.GiftCertificateRepository;
import com.epam.esm.data_access.repository.GiftTagRepository;
import com.epam.esm.data_access.repository.impl.GiftCertificateRepositoryImpl;
import com.epam.esm.data_access.repository.impl.GiftTagRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Configuration
@ComponentScan(basePackages = "test.com.epam.esm.data_access")
public class DataAccessConfiguration {
    @Autowired
    @Qualifier("giftTagRowMapper")
    private RowMapper<GiftTag> tagRowMapper;
    @Autowired
    @Qualifier("giftCertificateRowMapperWithCheck")
    private RowMapper<GiftCertificate> certificateRowMapperWithCheck;
    @Autowired
    @Qualifier("giftTagRowMapperWithCheck")
    private RowMapper<GiftTag> tagRowMapperWithCheck;

    private GiftCertificate getGiftCertificate(ResultSet rs) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(rs.getLong(GiftCertificateTableColumnName.TABLE_NAME +
                GiftCertificateTableColumnName.ALIAS_DELIMITER +
                GiftCertificateTableColumnName.COLUMN_ID));
        certificate.setName(rs.getString(GiftCertificateTableColumnName.TABLE_NAME +
                GiftCertificateTableColumnName.ALIAS_DELIMITER +
                GiftCertificateTableColumnName.COLUMN_NAME));
        certificate.setDescription(rs.getString(GiftCertificateTableColumnName.TABLE_NAME +
                GiftCertificateTableColumnName.ALIAS_DELIMITER +
                GiftCertificateTableColumnName.COLUMN_DESCRIPTION));
        certificate.setPrice(rs.getDouble(GiftCertificateTableColumnName.TABLE_NAME +
                GiftCertificateTableColumnName.ALIAS_DELIMITER +
                GiftCertificateTableColumnName.COLUMN_PRICE));
        certificate.setDuration(rs.getInt(GiftCertificateTableColumnName.TABLE_NAME +
                GiftCertificateTableColumnName.ALIAS_DELIMITER +
                GiftCertificateTableColumnName.COLUMN_DURATION));
        certificate.setCreateDate(rs.getTimestamp(GiftCertificateTableColumnName.TABLE_NAME +
                GiftCertificateTableColumnName.ALIAS_DELIMITER +
                GiftCertificateTableColumnName.COLUMN_CREATE_DATE).toLocalDateTime());
        certificate.setLastUpdateDate(rs.getTimestamp(GiftCertificateTableColumnName.TABLE_NAME +
                GiftCertificateTableColumnName.ALIAS_DELIMITER +
                GiftCertificateTableColumnName.COLUMN_LAST_UPDATE_DATE).toLocalDateTime());
        return certificate;
    }


    @Bean
    protected DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
    }

    @Bean
    protected ResultSetExtractor<List<GiftCertificate>> listResultSetExtractor() {
        return rs -> {
            List<GiftCertificate> list = new LinkedList<>();
            while (rs.next()) {
                GiftCertificate certificate = getGiftCertificate(rs);
                do {
                    GiftCertificate tempCertificate = getGiftCertificate(rs);
                    if (!certificate.getName().equals(tempCertificate.getName())) {
                        list.add(certificate);
                        certificate = tempCertificate;
                    }
                    GiftTag giftTag = tagRowMapper.mapRow(rs, 0);
                    if (!certificate.getTagList().contains(giftTag)) {
                        certificate.addTag(giftTag);
                    }
                } while (rs.next());
                list.add(certificate);
            }
            return list;
        };
    }

    @Bean
    protected ResultSetExtractor<GiftCertificate> giftCertificateResultSetExtractor() {
        return rs -> {
            int rowNum = 0;
            GiftCertificate certificate = certificateRowMapperWithCheck.mapRow(rs, rowNum);
            if (certificate != null) {
                do {
                    certificate.addTag(tagRowMapper.mapRow(rs, rowNum));
                } while (rs.next());
            }
            return certificate;
        };
    }

    @Bean
    protected ResultSetExtractor<GiftTag> giftTagResultSetExtractor() {
        return rs -> tagRowMapperWithCheck.mapRow(rs, 0);
    }

    @Bean
    protected RowMapper<GiftTag> giftTagRowMapper() {
        return (rs, rowNum) -> {
            GiftTag giftTag = new GiftTag();
            giftTag.setId(rs.getLong(TagTableColumnName.TABLE_NAME +
                    TagTableColumnName.ALIAS_DELIMITER +
                    TagTableColumnName.COLUMN_ID));
            giftTag.setName(rs.getString(TagTableColumnName.TABLE_NAME +
                    TagTableColumnName.ALIAS_DELIMITER +
                    TagTableColumnName.COLUMN_NAME));
            return giftTag;
        };
    }

    @Bean
    protected RowMapper<GiftCertificate> giftCertificateRowMapper() {
        return (rs, rowNum) -> {
            GiftCertificate certificate = getGiftCertificate(rs);
            do {
                GiftCertificate tempCertificate = getGiftCertificate(rs);
                if (!certificate.equals(tempCertificate)) {
                    return certificate;
                }
                certificate.addTag(tagRowMapper.mapRow(rs, rowNum));
            } while (rs.next());
            return certificate;
        };
    }

    @Bean
    protected RowMapper<GiftTag> giftTagRowMapperWithCheck() {
        return (rs, rowNum) -> {
            GiftTag giftTag = null;
            if (rs.next()) {
                giftTag = new GiftTag();
                giftTag.setId(rs.getLong(TagTableColumnName.TABLE_NAME +
                        TagTableColumnName.ALIAS_DELIMITER +
                        TagTableColumnName.COLUMN_ID));
                giftTag.setName(rs.getString(TagTableColumnName.TABLE_NAME +
                        TagTableColumnName.ALIAS_DELIMITER +
                        TagTableColumnName.COLUMN_NAME));
            }
            return giftTag;
        };
    }

    @Bean
    protected RowMapper<GiftCertificate> giftCertificateRowMapperWithCheck() {
        return (rs, rowNum) -> {
            GiftCertificate certificate = null;
            if (rs.next()) {
                certificate = getGiftCertificate(rs);
                do {
                    GiftCertificate tempCertificate = getGiftCertificate(rs);
                    if (!certificate.equals(tempCertificate)) {
                        return certificate;
                    }
                    certificate.addTag(tagRowMapper.mapRow(rs, rowNum));
                } while (rs.next());
            }
            return certificate;
        };
    }

    @Bean
    @Autowired
    protected GiftCertificateRepository giftCertificateRepository(DataSource dataSource,
                                                                  ResultSetExtractor<GiftCertificate> resultSetExtractor,
                                                                  GiftTagRepository giftTagRepository,
                                                                  ResultSetExtractor<List<GiftCertificate>> listExtractor) {
        return new GiftCertificateRepositoryImpl(dataSource, resultSetExtractor, giftTagRepository, listExtractor);
    }

    @Bean
    @Autowired
    protected GiftTagRepository giftTagRepository(DataSource dataSource,
                                                  ResultSetExtractor<GiftTag> resultSetExtractor) {
        return new GiftTagRepositoryImpl(dataSource, resultSetExtractor);
    }
}
