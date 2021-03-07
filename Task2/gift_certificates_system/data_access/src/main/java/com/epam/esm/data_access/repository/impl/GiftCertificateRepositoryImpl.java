package com.epam.esm.data_access.repository.impl;

import com.epam.esm.data_access.entity.GiftCertificate;
import com.epam.esm.data_access.entity.GiftTag;
import com.epam.esm.data_access.entity.table.GiftCertificateTableColumnName;
import com.epam.esm.data_access.entity.table.TagTableColumnName;
import com.epam.esm.data_access.repository.GiftCertificateRepository;
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
import java.util.HashSet;
import java.util.List;

@Repository
public class GiftCertificateRepositoryImpl implements GiftCertificateRepository {
    private static final String LIKE_WRAPPER = "%";
    private static final String QUERY_FIND_BY_ID = "SELECT " +
            "gc.id                  AS gift_certificate_id, " +
            "gc.name                AS gift_certificate_name," +
            "gc.description         AS gift_certificate_description," +
            "gc.price               AS gift_certificate_price," +
            "gc.duration            AS gift_certificate_duration," +
            "gc.create_date         AS gift_certificate_create_date," +
            "gc.last_update_date    AS gift_certificate_last_update_date, " +
            "t.id                   AS tag_id, " +
            "t.name                 AS tag_name " +
            "FROM public.gift_certificate AS gc " +
            "INNER JOIN public.gift_certificate_tag AS gct " +
            "   ON gct.id_gift_certificate = gc.id " +
            "INNER JOIN public.tag                  AS t " +
            "   ON t.id = gct.id_tag " +
            "WHERE gc.id = :id";
    private static final String QUERY_FIND_BY_NAME = "SELECT " +
            "gc.id                  AS gift_certificate_id, " +
            "gc.name                AS gift_certificate_name," +
            "gc.description         AS gift_certificate_description," +
            "gc.price               AS gift_certificate_price," +
            "gc.duration            AS gift_certificate_duration," +
            "gc.create_date         AS gift_certificate_create_date," +
            "gc.last_update_date    AS gift_certificate_last_update_date, " +
            "t.id                   AS tag_id, " +
            "t.name                 AS tag_name " +
            "FROM public.gift_certificate AS gc " +
            "LEFT JOIN public.gift_certificate_tag AS gct " +
            "   ON gct.id_gift_certificate = gc.id " +
            "LEFT JOIN public.tag                  AS t " +
            "   ON t.id = gct.id_tag " +
            "WHERE gc.name = :name";
    private static final String QUERY_SAVE = "INSERT INTO public.gift_certificate (" +
            "name, " +
            "description, " +
            "price, " +
            "duration, " +
            "create_date, " +
            "last_update_date" +
            ") VALUES (:name,:description,:price,:duration,:createDate,:lastUpdateDate)";
    private static final String QUERY_SAVE_FOREIGN_KEY = "INSERT INTO public.gift_certificate_tag(" +
            "id_gift_certificate, " +
            "id_tag" +
            ") VALUES (:id_gift_certificate, :id_tag)";
    private static final String QUERY_UPDATE = "UPDATE public.gift_certificate SET " +
            "name = COALESCE(:name, name), " +
            "description = COALESCE(:description, description), " +
            "price = CASE WHEN :price = 0 THEN price ELSE :price END, " +
            "duration = CASE WHEN :duration = 0 THEN duration ELSE :duration END, " +
            "last_update_date = COALESCE(:lastUpdateDate, last_update_date)" +
            "WHERE name = :name";
    private static final String QUERY_DELETE = "DELETE FROM public.gift_certificate_tag " +
            "WHERE id_gift_certificate = :id; " +
            "DELETE FROM public.gift_certificate " +
            "WHERE id = :id";
    private static final String QUERY_FIND_ALL_BY_TAG_NAME = "SELECT  DISTINCT " +
            "gc.id                      AS gift_certificate_id, " +
            "gc.name                    AS gift_certificate_name," +
            "gc.description             AS gift_certificate_description," +
            "gc.price                   AS gift_certificate_price," +
            "gc.duration                AS gift_certificate_duration," +
            "gc.create_date             AS gift_certificate_create_date," +
            "gc.last_update_date        AS gift_certificate_last_update_date, " +
            "t_all.id                   AS tag_id, " +
            "t_all.name                 AS tag_name " +
            "FROM public.tag AS t " +
            "INNER JOIN public.gift_certificate_tag AS gct " +
            "   ON gct.id_tag = t.id " +
            "       AND t.name = :name " +
            "INNER JOIN public.gift_certificate     AS gc " +
            "   ON gc.id = gct.id_gift_certificate " +
            "INNER JOIN public.gift_certificate_tag AS gct_all " +
            "   ON gct_all.id_gift_certificate = gc.id " +
            "INNER JOIN public.tag AS t_all " +
            "   ON t_all.id = gct_all.id_tag";
    private static final String QUERY_FIND_ALL_LIKE_CERTIFICATE_NAME = "SELECT " +
            "gc.id                  AS gift_certificate_id, " +
            "gc.name                AS gift_certificate_name," +
            "gc.description         AS gift_certificate_description," +
            "gc.price               AS gift_certificate_price," +
            "gc.duration            AS gift_certificate_duration," +
            "gc.create_date         AS gift_certificate_create_date," +
            "gc.last_update_date    AS gift_certificate_last_update_date, " +
            "t.id                   AS tag_id, " +
            "t.name                 AS tag_name " +
            "FROM public.gift_certificate AS gc " +
            "INNER JOIN public.gift_certificate_tag AS gct " +
            "   ON gct.id_gift_certificate = gc.id " +
            "INNER JOIN public.tag                  AS t " +
            "   ON t.id = gct.id_tag " +
            "WHERE gc.name LIKE :name";
    private static final String QUERY_FIND_ALL_LIKE_CERTIFICATE_DESCRIPTION = "SELECT " +
            "gc.id                  AS gift_certificate_id, " +
            "gc.name                AS gift_certificate_name," +
            "gc.description         AS gift_certificate_description," +
            "gc.price               AS gift_certificate_price," +
            "gc.duration            AS gift_certificate_duration," +
            "gc.create_date         AS gift_certificate_create_date," +
            "gc.last_update_date    AS gift_certificate_last_update_date, " +
            "t.id                   AS tag_id, " +
            "t.name                 AS tag_name " +
            "FROM public.gift_certificate AS gc " +
            "INNER JOIN public.gift_certificate_tag AS gct " +
            "   ON gct.id_gift_certificate = gc.id " +
            "INNER JOIN public.tag                  AS t " +
            "   ON t.id = gct.id_tag " +
            "WHERE gc.description LIKE :description";


    private final ResultSetExtractor<GiftCertificate> certificateExtractor;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final GiftTagRepository tagRepository;
    private final ResultSetExtractor<List<GiftCertificate>> listExtractor;

    @Autowired
    public GiftCertificateRepositoryImpl(DataSource dataSource,
                                         ResultSetExtractor<GiftCertificate> certificateExtractor,
                                         GiftTagRepository tagRepository,
                                         ResultSetExtractor<List<GiftCertificate>> listExtractor) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.certificateExtractor = certificateExtractor;
        this.tagRepository = tagRepository;
        this.listExtractor = listExtractor;
    }

    @Override
    public GiftCertificate findById(long id) {
        return namedParameterJdbcTemplate.query(QUERY_FIND_BY_ID,
                new MapSqlParameterSource().addValue(GiftCertificateTableColumnName.COLUMN_ID, id),
                certificateExtractor);
    }

    @Override
    public GiftCertificate findByName(String name) {
        return namedParameterJdbcTemplate.query(QUERY_FIND_BY_NAME,
                new MapSqlParameterSource().addValue(GiftCertificateTableColumnName.COLUMN_NAME, name),
                certificateExtractor);
    }

    @Transactional
    @Override
    public long save(GiftCertificate giftCertificate) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(QUERY_SAVE, new BeanPropertySqlParameterSource(giftCertificate), keyHolder,
                new String[]{GiftCertificateTableColumnName.COLUMN_ID});
        giftCertificate.setId(keyHolder.getKey().longValue());
        for (GiftTag giftTag : new HashSet<>(giftCertificate.getTagList())) {
            saveNonExistTag(giftTag);
            namedParameterJdbcTemplate.update(QUERY_SAVE_FOREIGN_KEY, new MapSqlParameterSource()
                    .addValue(GiftCertificateTableColumnName.COLUMN_ID +
                            GiftCertificateTableColumnName.ALIAS_DELIMITER +
                            GiftCertificateTableColumnName.TABLE_NAME, giftCertificate.getId())
                    .addValue(TagTableColumnName.COLUMN_ID +
                            TagTableColumnName.ALIAS_DELIMITER +
                            TagTableColumnName.TABLE_NAME, giftTag.getId()));
        }
        return giftCertificate.getId();
    }

    @Transactional
    @Override
    public boolean updateByName(GiftCertificate giftCertificate) {
        GiftCertificate certificateFromDb = findByName(giftCertificate.getName());
        if (certificateFromDb == null) {
            return false;
        } else {
            giftCertificate.setId(certificateFromDb.getId());
        }
        for (GiftTag giftTag : new HashSet<>(giftCertificate.getTagList())) {
            saveNonExistTag(giftTag);
        }
        return namedParameterJdbcTemplate.update(QUERY_UPDATE,
                new BeanPropertySqlParameterSource(giftCertificate)) > 0;
    }

    @Transactional
    @Override
    public boolean deleteById(long id) {
        return namedParameterJdbcTemplate.update(QUERY_DELETE,
                new MapSqlParameterSource().addValue(GiftCertificateTableColumnName.COLUMN_ID, id)) > 0;
    }

    private void saveNonExistTag(GiftTag giftTag) {
        GiftTag giftTagFromDb = tagRepository.findByName(giftTag.getName());
        if (giftTagFromDb == null) {
            giftTag.setId(tagRepository.save(giftTag));
        } else {
            giftTag.setId(giftTagFromDb.getId());
        }
    }

    @Override
    public List<GiftCertificate> findAllByTagName(String name) {
        return namedParameterJdbcTemplate.query(QUERY_FIND_ALL_BY_TAG_NAME,
                new MapSqlParameterSource().addValue(GiftCertificateTableColumnName.COLUMN_NAME, name),
                listExtractor);
    }

    @Override
    public List<GiftCertificate> findAllByPartOfCertificateName(String part) {
        return namedParameterJdbcTemplate.query(QUERY_FIND_ALL_LIKE_CERTIFICATE_NAME,
                new MapSqlParameterSource().addValue(GiftCertificateTableColumnName.COLUMN_NAME,
                        LIKE_WRAPPER + part + LIKE_WRAPPER),
                listExtractor);
    }

    @Override
    public List<GiftCertificate> findAllByPartOfCertificateDescription(String part) {
        return namedParameterJdbcTemplate.query(QUERY_FIND_ALL_LIKE_CERTIFICATE_DESCRIPTION,
                new MapSqlParameterSource().addValue(GiftCertificateTableColumnName.COLUMN_DESCRIPTION,
                        LIKE_WRAPPER + part + LIKE_WRAPPER),
                listExtractor);
    }
}
