package com.epam.esm.data_access.repository.impl;

import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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
            "gc.last_update_date " +
            "FROM public.gift_certificate AS gc " +
            "INNER JOIN public.gift_certificate_tag AS gst " +
            "   ON gc.id = gst.id_gift_certificate " +
            "INNER JOIN public.tag AS t " +
            "   ON gst.id_gift_certificate = t.id " +
            "WHERE gc.id = ?";
    private static final String QUERY_SAVE = "INSERT INTO public.gift_certificate (" +
            "id, " +
            "name, " +
            "description, " +
            "price, " +
            "duration, " +
            "create_date, " +
            "last_update_date " +
            ") VALUES (?,?,?,?,?,?,?)";
    private static final String QUERY_UPDATE = "UPDATE public.gift_certificate " +
            "SET name = ?, " +
            "description = ?, " +
            "price = ?, " +
            "duration = ?, " +
            "create_date = ?, " +
            "last_update_date = ?" +
            "WHERE id = ?";
    private static final String QUERY_DELETE_GIFT_CERTIFICATE_BY_ID = "DELETE FROM public.gift_certificate " +
            "WHERE id = ?";
    private static final String QUERY_DELETE_GIFT_CERTIFICATE_TAG_BY_GIFT_CERTIFICATE_ID =
            "DELETE FROM public.gift_certificate_tag " +
                    "WHERE id_gift_certificate = ?";

    private final ResultSetExtractor<GiftCertificate> certificateRowMapper;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateRepositoryImpl(DataSource dataSource,
                                         ResultSetExtractor<GiftCertificate> certificateExtractor) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.certificateRowMapper = certificateExtractor;
    }

    @Override
    public GiftCertificate findById(long id) {
        return jdbcTemplate.query(QUERY_FIND_BY_ID, certificateRowMapper, id);
    }

    @Override
    public boolean save(GiftCertificate giftCertificate) {
        return jdbcTemplate.update(QUERY_SAVE,
                giftCertificate.getId(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate()) > 0;
    }

    @Override
    public boolean updateById(GiftCertificate giftCertificate) {
        return jdbcTemplate.update(QUERY_UPDATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getId()) > 0;
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        return jdbcTemplate.update(QUERY_DELETE_GIFT_CERTIFICATE_TAG_BY_GIFT_CERTIFICATE_ID, id) > 0 &&
                jdbcTemplate.update(QUERY_DELETE_GIFT_CERTIFICATE_BY_ID, id) > 0;
    }
}
