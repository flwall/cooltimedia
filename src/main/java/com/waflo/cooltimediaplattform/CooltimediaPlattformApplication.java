package com.waflo.cooltimediaplattform;

import com.waflo.cooltimediaplattform.model.Category;
import com.waflo.cooltimediaplattform.model.Movie;
import com.waflo.cooltimediaplattform.model.Person;
import com.waflo.cooltimediaplattform.repository.CategoryRepository;
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
    public CommandLineRunner loadData(MovieRepository repository, PersonRepository personRepository, CategoryRepository categoryRepository) {
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
            var cat=new Category();
            cat.setName("Sample");


            categoryRepository.save(cat);

            mov.setCategory(cat);
            mov.setSummary("This is just a static Sample inserted by dev");
            repository.save(mov);
            var movie = new Movie();
            movie.setTitle("The Dark Knight Returns");movie.setPublishDate(LocalDate.of(2012, 12, 12));
            movie.setSummary("This is another Summary of The Dark knight returns");
            movie.setCategory(cat);
            movie.setAuthor(auth);
            repository.save(movie);
        };
    }
}
