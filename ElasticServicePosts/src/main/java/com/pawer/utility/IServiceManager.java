package com.pawer.utility;

import java.util.Optional;

public interface IServiceManager <T,ID>{
    T save(T t);
    T update(T t);
    void delete(T t);
    void deleteById(ID id);
    Iterable<T> findAll();
    Optional<T> findById(ID id);

}
