package com.epam.esm.data_access.repository.impl;

import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String QUERY_FIND_BY_ID = "SELECT " +
            "gc.id," +
            "gc.name," +
            "gc.description," +
            "gc.price," +
            "gc.duration," +
            "gc.create_date," +
            "gc.last_update_date, " +
            "t.id , " +
            "t.name " +
            "FROM public.gift_certificate AS gc " +
            "INNER JOIN public.gift_certificate_tag AS gct " +
            "   ON gct.id_gift_certificate = gc.id " +
            "INNER JOIN public.tag AS t " +
            "   ON t.id = gct.id_tag " +
            "WHERE gc.id = :id";
    private static final String QUERY_SAVE = "INSERT INTO public.gift_certificate (" +
            "name, " +
            "description, " +
            "price, " +
            "duration, " +
            "create_date, " +
            "last_update_date" +
            ") VALUES (:name,:description,:price,:duration,:createDate,:lastUpdateDate)";
    private static final String QUERY_UPDATE = "UPDATE public.gift_certificate SET " +
            "name = COALESCE(:name, name), " +
            "description = COALESCE(:description, description), " +
            "price = CASE WHEN :price = 0 THEN price ELSE :price END, " +
            "duration = CASE WHEN :duration = 0 THEN duration ELSE :duration END, " +
            "last_update_date = COALESCE(:lastUpdateDate, last_update_date)" +
            "WHERE id = :id";
    private static final String QUERY_DELETE_GIFT_CERTIFICATE_BY_ID = "DELETE FROM public.gift_certificate " +
            "WHERE id = :id";
    private static final String QUERY_DELETE_GIFT_CERTIFICATE_TAG_BY_GIFT_CERTIFICATE_ID =
            "DELETE FROM public.gift_certificate_tag " +
                    "WHERE id_gift_certificate = :id";

    private final ResultSetExtractor<GiftCertificate> certificateExtractor;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateRepositoryImpl(DataSource dataSource,
                                         @Qualifier("giftCertificateResultSetExtractor")
                                                 ResultSetExtractor<GiftCertificate> certificateExtractor) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.certificateExtractor = certificateExtractor;
    }

    @Override
    public GiftCertificate findById(long id) {
        return jdbcTemplate.query(QUERY_FIND_BY_ID,
                new MapSqlParameterSource().addValue("id", id),
                certificateExtractor);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public long save(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(QUERY_SAVE, new BeanPropertySqlParameterSource(giftCertificate), keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    @Override
    public boolean updateById(GiftCertificate giftCertificate) {
        return jdbcTemplate.update(QUERY_UPDATE, new BeanPropertySqlParameterSource(giftCertificate)) > 0;
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        SqlParameterSource source = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.update(QUERY_DELETE_GIFT_CERTIFICATE_TAG_BY_GIFT_CERTIFICATE_ID, source) > 0 &&
                jdbcTemplate.update(QUERY_DELETE_GIFT_CERTIFICATE_BY_ID, source) > 0;
    }
}
