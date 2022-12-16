package com.epam.repository.impl;

import com.epam.model.entity.Tag;
import com.epam.repository.TagRepo;
import com.epam.repository.mapper.TagRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static com.epam.repository.query.QuerySQL.*;

@Repository
public class TagRepoImpl implements TagRepo {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagRepoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SELECT_ALL_TAGS, new TagRowMapper());
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        List<Tag> list = jdbcTemplate.query(SELECT_TAG_BY_ID, new TagRowMapper(), id);
               return !list.isEmpty() ? Optional.of(list.get(0)) :
                Optional.empty();
    }

    @Override
    public void create(Tag data) {
        jdbcTemplate.update(CREATE_TAG, data.getName());
    }



    @Override
    public boolean delete(Integer id) {
        return jdbcTemplate.update(DELETE_TAG, id) != 0;
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.query(DELETE_TAG, new TagRowMapper());
    }

    @Override
    public Optional<Tag> findByName(String name) {
        List<Tag> list = jdbcTemplate.query(SELECT_TAG_BY_NAME, new TagRowMapper(), name);
        return !list.isEmpty() ?
                Optional.of(list.get(0)) :
                Optional.empty();
    }
}
