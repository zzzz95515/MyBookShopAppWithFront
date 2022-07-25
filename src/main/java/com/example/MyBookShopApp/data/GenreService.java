package com.example.MyBookShopApp.data;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    public GenreService(GenreRepository repository) {
        this.repository = repository;
    }

    GenreRepository repository;

    public List<Genre> getGenres(){
        return repository.findAll();
    }

}
