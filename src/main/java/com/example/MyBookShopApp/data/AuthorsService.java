package com.example.MyBookShopApp.data;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorsService {



    private AuthorRepository repository;
    @Autowired
    public AuthorsService(AuthorRepository repository) {
        this.repository = repository;
    }

    public Map<String, List<Author>> getAuthorsMap() {
        List<Author> authors = repository.findAll();
        return  authors.stream().collect(Collectors.groupingBy((Author a) ->{
            return a.getLastName().substring(0,1);
        }));

    }

    public Author getAuthorById(Integer id){
        return repository.findAuthorById(id).get(0);
    }
}
