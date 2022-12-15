package com.epam.service;

import com.epam.exception.IncorrectParameterException;
import com.epam.model.entity.GiftCertificates;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

public interface GiftCertificatesService extends CommonService<GiftCertificates,Long>{
    Optional<GiftCertificates> findById(Integer id);

    void create(GiftCertificates giftCertificate);

    boolean delete(Integer id);

    boolean update(int id, GiftCertificates giftCertificate) throws IncorrectParameterException;

    boolean updateGiftTag(int id);

    List<GiftCertificates> doFilter(MultiValueMap<String, String> requestParams);


}
