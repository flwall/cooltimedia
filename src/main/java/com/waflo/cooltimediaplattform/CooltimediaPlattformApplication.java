package com.waflo.cooltimediaplattform;

import com.waflo.cooltimediaplattform.model.Movie;
import com.waflo.cooltimediaplattform.model.Person;
import com.waflo.cooltimediaplattform.repository.MovieRepository;
import com.waflo.cooltimediaplattform.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;


@SpringBootApplication
public class CooltimediaPlattformApplication {

    public static final Logger log = LoggerFactory.getLogger(CooltimediaPlattformApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CooltimediaPlattformApplication.class, args);
    }


    @Bean
    public CommandLineRunner loadData(MovieRepository repository, PersonRepository personRepository) {
        return (args) -> {
            var mov = new Movie();
            var auth = new Person();
            auth.setFirstName("Florian");
            auth.setLastName("Wall");
            auth.setBirthDate(LocalDate.of(2002, 6, 29));
            personRepository.save(auth);

            mov.setTitle("Batman Begins");
            mov.setPublishDate(LocalDate.now());
            mov.setAuthor(auth);
            repository.save(mov);

        };
    }
}
