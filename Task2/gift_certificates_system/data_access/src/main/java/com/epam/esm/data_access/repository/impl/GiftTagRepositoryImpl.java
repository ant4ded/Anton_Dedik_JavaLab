package com.epam.esm.data_access.repository.impl;

import com.epam.esm.data_access.entity.GiftTag;
import com.epam.esm.data_access.repository.GiftTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

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
    private static final String QUERY_DELETE = "DELETE FROM public.tag " +
            "WHERE id = ?";

    private final RowMapper<GiftTag> tagRowMapper;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GiftTagRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.tagRowMapper = BeanPropertyRowMapper.newInstance(GiftTag.class);
    }

    @Override
    public GiftTag findById(long id) {
        return jdbcTemplate.queryForObject(QUERY_FIND_BY_ID, tagRowMapper, id);
    }

    @Override
    public boolean save(GiftTag giftTag) {
        return jdbcTemplate.update(QUERY_SAVE,
                giftTag.getId(),
                giftTag.getName()) > 0;
    }

    @Override
    public boolean deleteById(GiftTag giftTag, long id) {
        return jdbcTemplate.update(QUERY_DELETE, id) > 0;
    }
}
