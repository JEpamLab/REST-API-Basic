package com.epam.service;

import com.epam.model.entity.GiftCertificates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


/*
 * @author Otabek Javqochdiyev
 * */
public interface CommonService<T, D> {
    public List<T> getAll() throws NullPointerException;

//    public Optional<T> getById(D id) throws Exception;

    public void deleteById(D id);
}
