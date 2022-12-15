package com.epam.service;

import com.epam.model.entity.Tag;

import java.util.Optional;

public interface TagService extends CommonService<Tag,Long>{
    void create(Tag data) throws Exception;

    Optional<Tag> findById(Integer id);
    boolean delete(Integer id);

    Optional<Tag> findByName(String name);
}
