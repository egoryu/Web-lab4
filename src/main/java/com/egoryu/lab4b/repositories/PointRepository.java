package com.egoryu.lab4b.repositories;

import com.egoryu.lab4b.entities.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends CrudRepository<Point, Long> {
}
