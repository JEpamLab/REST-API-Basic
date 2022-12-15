package com.epam.config;

import com.epam.controller.GiftCertificatesController;

import com.epam.repository.GiftCertificatesRepo;
import com.epam.repository.impl.GiftCertificatesRepoImpl;
import com.epam.service.impl.GiftCertificateServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
//@Bean
//    public GiftCertificatesRepoImpl giftCertificatesRepo(){
//    return new GiftCertificatesRepoImpl();
//}
//@Bean
//    public GiftCertificateServiceImpl giftCertificateService() {
//    return new GiftCertificateServiceImpl(giftCertificatesRepo());
//
//}
//
//@Bean
//    public GiftCertificatesController giftCertificatesController(){
//    return new GiftCertificatesController(giftCertificateService());
//}
}
