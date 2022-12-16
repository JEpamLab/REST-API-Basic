package com.epam.service;

import com.epam.model.entity.Tag;
import com.epam.repository.impl.TagRepoImpl;
import com.epam.service.impl.TagServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TagServiceTest {
    @Mock
    private TagService tagServiceImpl;
    @Mock
    private TagRepoImpl tagRepo;

    @BeforeEach
    public void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
        tagRepo = new TagRepoImpl(dataSource);
        tagServiceImpl = new TagServiceImpl(tagRepo);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("/drop.sql"));
    }

    @Test
    public void findAll() throws NullPointerException {
        List<Tag> tags = tagServiceImpl.getAll();
        assertEquals(5, tags.size());
    }

    @Test
    public void findById() throws NullPointerException {
        Optional<Tag> tagOptional = tagServiceImpl.findById(2);
        Tag tag = new Tag();
        if (tagOptional.isPresent()) {
            tag = tagOptional.get();
        }
        assertEquals("red", tag.getName());
    }


    @Test
    public void create() throws NullPointerException {
        Long id = 6L;
        Tag tagToCreate = new Tag(id, "Tag 6");
        System.out.println(tagToCreate);
        tagServiceImpl.create(tagToCreate);
        Optional<Tag> tagOptional = tagServiceImpl.findById(6);
        Tag tag = new Tag();
        if (tagOptional.isPresent()) {
            tag = tagOptional.get();
        }
        assertEquals(tag, tagToCreate);
    }

    @Test
    public void delete() throws NullPointerException {
        tagServiceImpl.delete(4);
        List<Tag> tags = tagServiceImpl.getAll();
        assertEquals(4, tags.size());
    }

    @Test
    public void findByName() throws NullPointerException {
        Optional<Tag> tagOptional = tagServiceImpl.findByName("red");
        Tag tag = new Tag();
        if (tagOptional.isPresent()) {
            tag = tagOptional.get();
        }
        assertEquals("red", tag.getName());
    }
}