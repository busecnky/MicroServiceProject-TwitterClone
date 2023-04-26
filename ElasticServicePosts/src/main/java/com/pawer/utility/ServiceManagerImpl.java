package com.pawer.utility;

import com.pawer.repository.entity.BaseEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class ServiceManagerImpl<T extends BaseEntity,ID> implements IServiceManager<T,ID>{

    private final ElasticsearchRepository<T,ID> repository;

    @Override
    public T save(T t) {
        t.setCreateDate(LocalDateTime.now().toString());
        t.setUpdateDate(LocalDateTime.now().toString());
        t.setState(true);
        return repository.save(t);
    }




    @Override
    public T update(T t) {
        t.setUpdateDate(LocalDateTime.now().toString());
        return repository.save(t);
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public Iterable<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }
}
