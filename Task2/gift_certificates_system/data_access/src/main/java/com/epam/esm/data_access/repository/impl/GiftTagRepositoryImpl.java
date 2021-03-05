package com.epam.esm.data_access.repository.impl;

import com.epam.esm.data_access.entity.GiftTag;
import com.epam.esm.data_access.repository.GiftTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
public class GiftTagRepositoryImpl implements GiftTagRepository {
    private static final String QUERY_FIND_BY_ID = "SELECT " +
            "t.id   AS tag_id, " +
            "t.name AS tag_name " +
            "FROM public.tag AS t " +
            "WHERE t.id = :id";
    private static final String QUERY_FIND_BY_NAME = "SELECT " +
            "t.id   AS tag_id, " +
            "t.name AS tag_name " +
            "FROM public.tag AS t " +
            "WHERE t.name = :name";
    private static final String QUERY_SAVE = "INSERT INTO public.tag (" +
            "name" +
            ") VALUES (:name)";
    private static final String QUERY_DELETE = "DELETE FROM public.gift_certificate_tag " +
            "WHERE id_tag = :id; " +
            "DELETE FROM public.tag " +
            "WHERE id = :id";

    private final ResultSetExtractor<GiftTag> tagExtractor;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public GiftTagRepositoryImpl(DataSource dataSource, ResultSetExtractor<GiftTag> tagExtractor) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.tagExtractor = tagExtractor;
    }

    @Override
    public GiftTag findById(long id) {
        return jdbcTemplate.query(QUERY_FIND_BY_ID,
                new MapSqlParameterSource().addValue("id", id),
                tagExtractor);
    }

    @Override
    public GiftTag findByName(String name) {
        return jdbcTemplate.query(QUERY_FIND_BY_NAME,
                new MapSqlParameterSource().addValue("name", name),
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
        return jdbcTemplate.update(QUERY_DELETE, new MapSqlParameterSource().addValue("id", id)) > 0;
    }
}
