package com.egoryu.lab4b.repositories;

import com.egoryu.lab4b.entities.BlackList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListRepository extends CrudRepository<BlackList, Long> {
    boolean existsByToken(String token);
}
