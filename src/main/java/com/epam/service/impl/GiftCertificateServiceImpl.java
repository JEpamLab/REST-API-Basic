package com.epam.service.impl;

import com.epam.exception.IncorrectParameterException;
import com.epam.model.entity.GiftCertificates;
import com.epam.repository.impl.GiftCertificatesRepoImpl;
import com.epam.service.GiftCertificatesService;
import com.epam.validator.GiftValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.repository.query.QueryParam.*;


@Service
@RequiredArgsConstructor
public class GiftCertificateServiceImpl implements GiftCertificatesService {
    private final GiftCertificatesRepoImpl giftCertificatesRepo;

    @Override
    public List<GiftCertificates> getAll() throws Exception {
        System.out.println("ishladi");
        return giftCertificatesRepo.findAll();
    }

    @Override
    public void deleteById(Long id) {

    }


    @Override
    public Optional<GiftCertificates> findById(Integer id) {
        return giftCertificatesRepo.findById(id);
    }

    @Override
    @Transactional
    public void create(GiftCertificates giftCertificate) {
        giftCertificatesRepo.create(giftCertificate);
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        return giftCertificatesRepo.delete(id);
    }

    @Override
    public boolean update(int id, GiftCertificates giftCertificate) throws IncorrectParameterException {
        //Get gift to update by ID
        Optional<GiftCertificates> certificate = giftCertificatesRepo.findById(id);
        //Extract Gift from Wrapper
        GiftCertificates gift = new GiftCertificates();
        if (certificate.isPresent()) {
            gift = certificate.get();
        }
        //Get Validated Gift
        GiftCertificates validatedGift = GiftValidator.validateForUpdate(gift, giftCertificate);
        LocalDateTime now = LocalDateTime.now();
        validatedGift.setLast_update_date(LocalDateTime.now());
        //Update GiftCertificate
        return giftCertificatesRepo.update(gift);
    }

    @Override
    public boolean updateGiftTag(int id) {
        return giftCertificatesRepo.updateGiftTag(id);
    }

    @Override
    public List<GiftCertificates> doFilter(MultiValueMap<String, String> requestParams) {
        System.out.println("service do filter");
        Map<String, String> map = new HashMap<>();
        map.put(TAG_NAME, getSingleRequestParameter(requestParams, TAG_NAME));
        map.put(PART_NAME, getSingleRequestParameter(requestParams, PART_NAME));
        map.put(PART_DESCRIPTION, getSingleRequestParameter(requestParams, PART_DESCRIPTION));
        map.put(SORT_BY_NAME, getSingleRequestParameter(requestParams, SORT_BY_NAME));
        map.put(SORT_BY_DATE, getSingleRequestParameter(requestParams, SORT_BY_DATE));
        return giftCertificatesRepo.getWithFilters(map);
    }

    private String getSingleRequestParameter(MultiValueMap<String, String> requestParams, String parameter) {
        if (requestParams.containsKey(parameter)) {
            return requestParams.get(parameter).get(0);
        } else {
            return null;
        }
    }
}
