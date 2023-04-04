package com.pawer.utility;

import java.util.List;
import java.util.Optional;

public interface IServiceManager <T,ID>{
    T save(T t);
    T update(T t);
    void delete(T t);
    void deleteById(ID id);
    List<T> findAll();
    Optional<T> findById(ID id);

}
