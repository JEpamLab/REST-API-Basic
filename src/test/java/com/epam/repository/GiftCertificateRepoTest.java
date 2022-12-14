package com.epam.repository;

import com.epam.model.entity.GiftCertificates;
import com.epam.model.entity.Tag;
import com.epam.repository.impl.GiftCertificatesRepoImpl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GiftCertificateRepoTest {

    private GiftCertificatesRepo giftCertificatesRepo;

    @BeforeEach
    public void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:data.sql")
                .build();
        giftCertificatesRepo = new GiftCertificatesRepoImpl(dataSource);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DataSource dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2).build();
        ScriptUtils.executeSqlScript(dataSource.getConnection(), new ClassPathResource("/drop.sql"));
    }


    @Test
    public void getGiftCertificateById() {
        Optional<GiftCertificates> giftCertificateOpt = giftCertificatesRepo.findById(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createDate = LocalDateTime.parse("2022-10-10 11:15:10", formatter);
        LocalDateTime lastUpdateDate = LocalDateTime.parse("2022-10-05 11:15:10", formatter);
        Long id = 5L;
        Tag tag = new Tag(id, "tagName5");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        GiftCertificates giftCertificateToCompare = new GiftCertificates(2L, "bgiftCertificate2", "description2", 2.22, 2, createDate, lastUpdateDate, tags);
        GiftCertificates giftCertificate = new GiftCertificates();

        if (giftCertificateOpt.isPresent()) {
            giftCertificate = giftCertificateOpt.get();
        }

        boolean check = giftCertificate.equals(giftCertificateToCompare);
        assertTrue(check);
    }

    @Test
    public void getAllGiftCertificates() {
        List<GiftCertificates> giftCertificates = giftCertificatesRepo.findAll();
        assertEquals(5, giftCertificates.size());
    }

    @Test

    public void updateGiftCertificate() {
        int targetGift = 3;
        //Get certificate to update
        Optional<GiftCertificates> certificate = giftCertificatesRepo.findById(targetGift);
        GiftCertificates giftCertificate = new GiftCertificates();
        if (certificate.isPresent()) {
            giftCertificate = certificate.get();
        }
        //Update it with new values
        giftCertificate.setName("Update name gift " + targetGift);
        giftCertificate.setDescription("Update desc gift" + targetGift);
        giftCertificatesRepo.update(giftCertificate);

        //Get updated GiftCertificate
        Optional<GiftCertificates> updatedCertificate = giftCertificatesRepo.findById(targetGift);
        GiftCertificates updatedGiftCertificate = new GiftCertificates();
        if (updatedCertificate.isPresent()) {
            updatedGiftCertificate = updatedCertificate.get();
        }

        String name = giftCertificate.getName();
        String updatedName = updatedGiftCertificate.getName();
        assertEquals(name, updatedName);
    }

    @Test
    public void deleteCertificate() {
        giftCertificatesRepo.delete(2);
        List<GiftCertificates> afterDelete = giftCertificatesRepo.findAll();
        assertEquals(4, afterDelete.size());
    }

    @Test
    public void createGiftCertificate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime createDate = LocalDateTime.parse("2022-10-17 11:15:10", formatter);
        LocalDateTime lastUpdateDate = LocalDateTime.parse("2022-10-05 11:15:10", formatter);
        Long id = 1L;
        Tag tag = new Tag(id, "tagName1");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        GiftCertificates giftCertificateToCreate = new GiftCertificates(5L, "egiftCertificate5", "description5", 5.55, 2, createDate, lastUpdateDate, tags);
        giftCertificatesRepo.create(giftCertificateToCreate);
        Optional<GiftCertificates> giftCertificateToFindOpt = giftCertificatesRepo.findById(5);
        GiftCertificates giftCertificateToFind = new GiftCertificates();
        if (giftCertificateToFindOpt.isPresent()) {
            giftCertificateToFind = giftCertificateToFindOpt.get();
        }
        boolean check = giftCertificateToCreate.equals(giftCertificateToFind);
        assertTrue(check);
    }

    @Test
    public void findCertificatesByNameDESC() {
        Map<String, String> query = new HashMap<>();
        query.put("sortByName", "DESC");
        List<GiftCertificates> list = giftCertificatesRepo.getWithFilters(query);
        assertEquals("egiftCertificate5", list.get(0).getName());
    }

    @Test
    public void findCertificatesByNameASC() {
        Map<String, String> query = new HashMap<>();
        query.put("sortByName", "ASC");
        List<GiftCertificates> list = giftCertificatesRepo.getWithFilters(query);
        assertEquals("agiftCertificate1", list.get(0).getName());
    }

    @Test
    public void findCertificatesByDateASC() {
        Map<String, String> query = new HashMap<>();
        query.put("sortByDate", "ASC");
        List<GiftCertificates> list = giftCertificatesRepo.getWithFilters(query);
        assertEquals("dgiftCertificate4", list.get(0).getName());
    }

    @Test
    public void findCertificatesByDateDESC() {
        Map<String, String> query = new HashMap<>();
        query.put("sortByDate", "DESC");
        List<GiftCertificates> list = giftCertificatesRepo.getWithFilters(query);
        assertEquals("egiftCertificate5", list.get(0).getName());
    }

    @Test
    public void findCertificatesByPartName() {
        Map<String, String> query = new HashMap<>();
        query.put("partName", "agiftCertificate1");
        List<GiftCertificates> list = giftCertificatesRepo.getWithFilters(query);
        assertEquals(1, list.size());
    }

    @Test
    public void findCertificatesByDescName() {
        Map<String, String> query = new HashMap<>();
        query.put("partDescription", "description2");
        List<GiftCertificates> list = giftCertificatesRepo.getWithFilters(query);
        assertEquals(1, list.size());
    }
}