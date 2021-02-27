package com.epam.esm.data_access.repository.impl;

import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.entity.GiftTag;
import com.epam.esm.data_access.repository.GiftCertificateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String QUERY_FIND_BY_ID = "SELECT " +
            "gs.id," +
            "gs.name," +
            "gs.description," +
            "gs.price," +
            "gs.duration," +
            "gs.create_date," +
            "gs.last_update_date " +
            "FROM public.gift_certificate AS gs " +
            "INNER JOIN public.gift_certificate_tag AS gst " +
            "   ON gs.id = gst.id_gift_certificate " +
            "INNER JOIN public.tag AS t " +
            "   ON gst.id_gift_certificate = t.id " +
            "WHERE gs.id = ?";
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
    private static final String QUERY_DELETE = "DELETE FROM public.gift_certificate " +
            "WHERE id = ?";

    private final RowMapper<GiftCertificate> certificateRowMapper;
    private final RowMapper<GiftTag> tagRowMapper;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftCertificateRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.certificateRowMapper = BeanPropertyRowMapper.newInstance(GiftCertificate.class);
        this.tagRowMapper = BeanPropertyRowMapper.newInstance(GiftTag.class);
    }

    @Override
    public GiftCertificate findById(long id) {
        return jdbcTemplate.query(QUERY_FIND_BY_ID,
                rs -> {
                    int row = 0;
                    GiftCertificate certificate = certificateRowMapper.mapRow(rs, row);
                    while (rs.next() && certificate != null) {
                        certificate.addTag(tagRowMapper.mapRow(rs, row++));
                    }
                    return certificate;
                }, id);
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
    public boolean updateById(GiftCertificate giftCertificate, long id) {
        return jdbcTemplate.update(QUERY_UPDATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getId()) > 0;
    }

    @Override
    public boolean deleteById(GiftCertificate giftCertificate, long id) {
        return jdbcTemplate.update(QUERY_DELETE, id) > 0;
    }
}
