package com.epam.repository;

import com.epam.model.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface  TagRepo extends CommonRepo<Tag, Long> {
    Optional<Tag> findByName(String name);

    public void deleteById(Long id);

}
