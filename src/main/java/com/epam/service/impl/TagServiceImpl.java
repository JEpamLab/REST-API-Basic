package com.epam.service.impl;

import com.epam.exception.IncorrectParameterException;
import com.epam.model.entity.GiftCertificates;
import com.epam.model.entity.Tag;
import com.epam.repository.impl.TagRepoImpl;
import com.epam.service.TagService;
import com.epam.validator.GiftValidator;
import com.epam.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagRepoImpl tagRepo;

    public TagServiceImpl(TagRepoImpl tagDaoImpl) {
    }


    @Override
    @Transactional
    public List<Tag> getAll() throws NullPointerException {
        return tagRepo.findAll();
    }




    @Override
    public void create(Tag data) throws NullPointerException {
        tagRepo.create(data);
    }




    @Override
    public boolean delete(Integer id) {
        tagRepo.delete(id);
        return true;
    }

    @Override
    public Optional<Tag> findById(Integer id) {
        System.out.println("Service");
        return tagRepo.findById(id);
    }
    @Override
    public void deleteById(Long id) {
        tagRepo.deleteById(id);
    }

    @Override
    public Optional<Tag> findByName(String name) {
        return tagRepo.findByName(name);
    }
}
