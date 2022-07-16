package com.example.MyBookShopApp.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


public interface TestEntityCrudRepository extends CrudRepository<TestEntity,Long> {
}
