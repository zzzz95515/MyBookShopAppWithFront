package com.example.MyBookShopApp.data;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
public class TagService {
    TagRepository repository;


    public TagService(TagRepository repository) {
        this.repository = repository;
    }

    public List<Tag> getTags(){
        List<Tag> tags = repository.findAll();
        Logger.getLogger(TagService.class.getSimpleName()).info("ищем Тэги");
        return tags;
    }

}
