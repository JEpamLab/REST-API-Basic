package com.epam.controller;

import com.epam.controller.response.ResponseHandler;
import com.epam.controller.response.ResponseMessage;
import com.epam.exception.IncorrectParameterException;
import com.epam.model.entity.GiftCertificates;
import com.epam.service.impl.GiftCertificateServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
@RequestMapping(value = "/api/gift")
public class GiftCertificatesController {

    private final GiftCertificateServiceImpl giftCertificateService;

    public GiftCertificatesController(GiftCertificateServiceImpl giftCertificateService1) {
        this.giftCertificateService = giftCertificateService1;
    }

    Logger logger = LoggerFactory.getLogger(GiftCertificatesController.class.getName());

    //GET all
    @GetMapping
    public ResponseEntity<List<GiftCertificates>> getAll() throws Exception {

        return ResponseEntity.ok(giftCertificateService.getAll());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<GiftCertificates>> giftCertificatesByParameter(@RequestParam MultiValueMap<String, String> allRequestParams) {
        System.out.println(giftCertificateService.doFilter(allRequestParams));

        return ResponseEntity.ok(giftCertificateService.doFilter(allRequestParams));

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) throws Exception {
        Optional<GiftCertificates> giftCertificateOptional = giftCertificateService.findById(id);
        GiftCertificates giftCertificate;
        if (giftCertificateOptional.isPresent()) {
            giftCertificate = giftCertificateOptional.get();
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_RECEIVED, HttpStatus.OK, giftCertificate);
        } else {
            return ResponseHandler.generateResponse("Gift with id ( " + id + " ) was not found", HttpStatus.NOT_FOUND, "[]");
        }
    }

    //Post mapping
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> create(@RequestBody GiftCertificates entity) {
        giftCertificateService.create(entity);
        return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_CREATED, HttpStatus.OK);

    }

    @PatchMapping(path = "{id}", consumes = "application/json")
    public ResponseEntity<Object> updateGiftCertificate(@PathVariable("id") int id, @RequestBody GiftCertificates giftCertificate) throws IncorrectParameterException {
        boolean check = giftCertificateService.update(id, giftCertificate);
        if (check) {
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_UPDATED + id, HttpStatus.OK);
        } else {
            return ResponseHandler.generateResponse(ResponseMessage.UPDATE_ERROR + id, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        if (giftCertificateService.delete(id)) {
            return ResponseHandler.generateResponse(ResponseMessage.SUCCESSFULLY_DELETED + id, HttpStatus.OK);
        }
        return ResponseHandler.generateResponse(ResponseMessage.DELETE_ERROR + id, HttpStatus.INTERNAL_SERVER_ERROR);

    }


}
