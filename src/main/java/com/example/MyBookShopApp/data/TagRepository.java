package com.example.MyBookShopApp.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TagRepository  extends JpaRepository<Tag,Integer> {
//    List<Tag> find();
}
