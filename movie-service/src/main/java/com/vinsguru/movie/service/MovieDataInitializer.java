package com.vinsguru.movie.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service // import the movies for demo purposes
@ConditionalOnProperty(name = "app.import-movies", havingValue = "true")
public class MovieDataInitializer implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {

    }

}
