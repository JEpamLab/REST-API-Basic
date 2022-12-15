package com.epam.repository;

import com.epam.model.entity.GiftCertificates;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


public interface GiftCertificatesRepo extends CommonRepo<GiftCertificates, Long> {


    List<GiftCertificates> getWithFilters(Map<String, String> fields);


    //UPDATE operations
    boolean update(GiftCertificates giftCertificate);

    boolean updateGiftTag(int id);



}
