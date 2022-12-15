package com.epam.repository;


import java.util.List;
import java.util.Optional;

public interface CommonRepo<T, Long> {
    List<T> findAll();

    public Optional<T> findById(Integer id);

    public void create(T data);

    public boolean delete(Integer id);


}
