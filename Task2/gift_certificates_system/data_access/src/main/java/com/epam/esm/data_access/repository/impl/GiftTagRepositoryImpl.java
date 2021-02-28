package com.epam.esm.data_access.repository.impl;

import com.epam.esm.data_access.entity.GiftTag;
import com.epam.esm.data_access.repository.GiftTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

@Repository
public class GiftTagRepositoryImpl implements GiftTagRepository {
    private static final String QUERY_FIND_BY_ID = "SELECT " +
            "t.id," +
            "t.name " +
            "FROM public.tag AS t " +
            "WHERE t.id = ?";
    private static final String QUERY_SAVE = "INSERT INTO public.tag (" +
            "id, " +
            "name " +
            ") VALUES (?,?)";
    private static final String QUERY_DELETE_GIFT_TAG_BY_ID = "DELETE FROM public.tag " +
            "WHERE id = ?";
    private static final String QUERY_DELETE_GIFT_CERTIFICATE_TAG_BY_GIFT_TAG_ID =
            "DELETE FROM public.gift_certificate_tag " +
                    "WHERE id_tag = ?";

    private final ResultSetExtractor<GiftTag> tagExtractor;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftTagRepositoryImpl(DataSource dataSource, ResultSetExtractor<GiftTag> tagExtractor) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.tagExtractor = tagExtractor;
    }

    @Override
    public GiftTag findById(long id) {
        return jdbcTemplate.query(QUERY_FIND_BY_ID, tagExtractor, id);
    }

    @Override
    public boolean save(GiftTag giftTag) {
        return jdbcTemplate.update(QUERY_SAVE,
                giftTag.getId(),
                giftTag.getName()) > 0;
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        return jdbcTemplate.update(QUERY_DELETE_GIFT_CERTIFICATE_TAG_BY_GIFT_TAG_ID, id) > 0 &&
                jdbcTemplate.update(QUERY_DELETE_GIFT_TAG_BY_ID, id) > 0;
    }
}
