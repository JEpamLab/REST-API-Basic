package com.epam.repository.impl;

import com.epam.model.entity.GiftCertificates;
import com.epam.model.entity.Tag;
import com.epam.repository.GiftCertificatesRepo;
import com.epam.repository.TagRepo;
import com.epam.repository.mapper.GiftRowMapper;
import com.epam.repository.query.QueryCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.*;

import static com.epam.repository.query.QuerySQL.*;


@Repository
public class GiftCertificatesRepoImpl implements GiftCertificatesRepo {
    private final JdbcTemplate jdbcTemplate;
    private final TagRepo tagRepo;

    @Autowired
    public GiftCertificatesRepoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        tagRepo = new TagRepoImpl(dataSource);
    }

    @Override
    public List<GiftCertificates> findAll() {
        return jdbcTemplate.query(SELECT_ALL_GIFT_CERTIFICATES, new GiftRowMapper());
    }

    @Override
    public Optional<GiftCertificates> findById(Integer id) {
        List<GiftCertificates> results = jdbcTemplate.query(SELECT_BY_CERTIFICATES_ID, new GiftRowMapper(), id);
        return !results.isEmpty() ?
                Optional.of(results.get(0)) :
                Optional.empty();
    }

    @Override
    public List<GiftCertificates> getWithFilters(Map<String, String> fields) {

        QueryCreator queryCreator = new QueryCreator();
        String query = queryCreator.createGetQuery(fields);
        return jdbcTemplate.query(query, new GiftRowMapper());
    }

    //CREATE operations
    @Override
    public void create(GiftCertificates giftCertificate) {
        giftCertificate.setCreate_date(LocalDateTime.now());
        //Get ID of newly created GiftCertificate
        Long giftCertificateId = createGiftCertificate(giftCertificate);
        //Get TAGS name to check on uniqueness
        List<Tag> list = giftCertificate.getTags();
        //Create tags which linked to the GiftCertificate
        createTags(giftCertificateId, list);
    }

    //DELETE operations
    @Override
    public boolean delete(Integer id) {
        int giftTag = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_TAG_BY_ID, id);
        int giftCertificate = jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_BY_ID, id);

        return (giftCertificate & giftTag) == 1;
    }

    //UPDATE operations
    @Override
    public boolean update(GiftCertificates giftCertificate) {
        giftCertificate.setLast_update_date(LocalDateTime.now());

        return jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE,
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                LocalDateTime.now(),
                giftCertificate.getId()) != 0;
    }

    @Override
    public boolean updateGiftTag(int id) {
        return jdbcTemplate.update(UPDATE_GIFT_CERTIFICATE_TAG, id) != 0;
    }


    private Long createTag(Tag tag) {
        //Create Statement for Tag
        PreparedStatementCreatorFactory pscfTag = new PreparedStatementCreatorFactory(CREATE_TAG, Types.VARCHAR);
        //Call to get generated key of the new Tag
        pscfTag.setReturnGeneratedKeys(true);

        PreparedStatementCreator pscTag = pscfTag.newPreparedStatementCreator(
                Arrays.asList(
                        tag.getName()));


        GeneratedKeyHolder tagKeyHolder = new GeneratedKeyHolder();
        //Create new Tag in the DB
        jdbcTemplate.update(pscTag, tagKeyHolder);

//        int newId;
//        if (tagKeyHolder.getKeys().size() > 1) {
//            newId = (int) tagKeyHolder.getKeys().get("tag_id");
//        } else {
//            newId= tagKeyHolder.getKey().intValue();
//        }
        //Get ID of newly created GiftCertificate
        return (Long) Objects.requireNonNull(tagKeyHolder.getKeys().get("tag_id"));
//        return newId;
    }

    private Long createGiftCertificate(GiftCertificates giftCertificate) {
        //Create Statement for GiftCertificate
        PreparedStatementCreatorFactory pscfGift = new PreparedStatementCreatorFactory(
                CREATE_GIFT_CERTIFICATE,
                Types.VARCHAR, Types.VARCHAR, Types.DOUBLE, Types.INTEGER, Types.TIMESTAMP, Types.TIMESTAMP
        );
        //Call to get generated key of the new GiftCertificate
        pscfGift.setReturnGeneratedKeys(true);

        PreparedStatementCreator pscGift = pscfGift.newPreparedStatementCreator(
                Arrays.asList(
                        giftCertificate.getName(),
                        giftCertificate.getDescription(),
                        giftCertificate.getPrice(),
                        giftCertificate.getDuration(),
                        giftCertificate.getCreate_date(),
                        giftCertificate.getLast_update_date()));

        GeneratedKeyHolder giftKeyHolder = new GeneratedKeyHolder();
        //Create new Gift in DB
        jdbcTemplate.update(pscGift, giftKeyHolder);
//        int newId;
//        if (giftKeyHolder.getKeys().size() > 1) {
//            newId = (int) giftKeyHolder.getKeys().get("id");
//        } else {
//            newId= giftKeyHolder.getKey().intValue();
//        }
        //Get ID of newly created GiftCertificate
        return (Long) Objects.requireNonNull(giftKeyHolder.getKeys().get("id"));
//        return newId;
    }

    private void createTags(Long giftCertificateId, List<Tag> tags) {
        //Pass through the List of gifts
        for (Tag tag : tags) {
            //Get name of each tag
            String name = tag.getName();
            //Find tag by name if it exists
            Optional<Tag> optTag = tagRepo.findByName(name);
            if (optTag.isPresent()) {
                //If tag exists we will pass it into table directly
                if (Objects.equals(tag.getName(), name)) {
                    Long tagId = tag.getId();
                    jdbcTemplate.update(CREATE_GIFT_WITH_TAG, giftCertificateId, tagId);
                }
            } else {
                //If tag does not exist we will create it and then pass to the table
                Long tagId = createTag(tag);
                jdbcTemplate.update(CREATE_GIFT_WITH_TAG, giftCertificateId, tagId);
            }
        }
    }
}
