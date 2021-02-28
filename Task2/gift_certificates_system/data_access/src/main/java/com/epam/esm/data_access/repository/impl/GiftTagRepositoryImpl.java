package com.epam.esm.data_access.repository.impl;

import com.epam.esm.data_access.entity.GiftTag;
import com.epam.esm.data_access.repository.GiftTagRepository;
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
public class GiftTagRepositoryImpl implements GiftTagRepository {
    private static final String QUERY_FIND_BY_ID = "SELECT " +
            "t.id," +
            "t.name " +
            "FROM public.tag AS t " +
            "WHERE t.id = :id";
    private static final String QUERY_SAVE = "INSERT INTO public.tag (" +
            "id, " +
            "name" +
            ") VALUES (:id,:name)";
    private static final String QUERY_DELETE_GIFT_TAG_BY_ID = "DELETE FROM public.tag " +
            "WHERE id = :id";
    private static final String QUERY_DELETE_GIFT_CERTIFICATE_TAG_BY_GIFT_TAG_ID =
            "DELETE FROM public.gift_certificate_tag " +
                    "WHERE id_tag = :id";

    private final ResultSetExtractor<GiftTag> tagExtractor;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public GiftTagRepositoryImpl(DataSource dataSource, @Qualifier("giftTagResultSetExtractor") ResultSetExtractor<GiftTag> tagExtractor) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.tagExtractor = tagExtractor;
    }

    @Override
    public GiftTag findById(long id) {
        return jdbcTemplate.query(QUERY_FIND_BY_ID,
                new MapSqlParameterSource().addValue("id", id),
                tagExtractor);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public long save(GiftTag giftTag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(QUERY_SAVE, new BeanPropertySqlParameterSource(giftTag), keyHolder);
        return keyHolder.getKeyAs(Long.class);
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        SqlParameterSource source = new MapSqlParameterSource().addValue("id", id);
        return jdbcTemplate.update(QUERY_DELETE_GIFT_CERTIFICATE_TAG_BY_GIFT_TAG_ID, source) > 0 &&
                jdbcTemplate.update(QUERY_DELETE_GIFT_TAG_BY_ID, source) > 0;
    }
}