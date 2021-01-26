package com.waflo.cooltimediaplattform;

import com.waflo.cooltimediaplattform.model.Category;
import com.waflo.cooltimediaplattform.model.File;
import com.waflo.cooltimediaplattform.model.Movie;
import com.waflo.cooltimediaplattform.model.Person;
import com.waflo.cooltimediaplattform.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.FileInputStream;
import java.time.LocalDate;


@SpringBootApplication
public class CooltimediaPlattformApplication {

    public static final Logger log = LoggerFactory.getLogger(CooltimediaPlattformApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CooltimediaPlattformApplication.class, args);
    }


    @Bean
    public CommandLineRunner loadData(FileContentStore store, FileRepository fileRepo, MovieRepository repository, PersonRepository personRepository, CategoryRepository categoryRepository) {
        return (args) -> {
            var mov = new Movie();
            var auth = new Person();
            auth.setName("Florian Wall");

            auth.setBirthDate(LocalDate.of(2002, 6, 29));
            personRepository.save(auth);

            mov.setTitle("Batman Begins");
            mov.setPublishDate(LocalDate.now());
            mov.setAuthor(auth);
            mov.setStream(new File());
            mov.setThumbnail(new File());
            var cat = new Category();
            cat.setName("Sample");


            categoryRepository.save(cat);

            mov.setCategory(cat);
            mov.setSummary("This is just a static Sample inserted by dev");
            var s1Stream = new File();
            s1Stream.setName("Sample 1");
            s1Stream.setMimeType("video/mov");
            store.setContent(s1Stream, new FileInputStream("./test-data/sample1.mov"));
            fileRepo.save(s1Stream);
            mov.setStream(s1Stream);
            File s1Thumb = new File();
            s1Thumb.setName("Sample 1 Thumb");
            s1Thumb.setMimeType("image/jpg");
            store.setContent(s1Thumb, new FileInputStream("./test-data/sample1.jpg"));
            fileRepo.save(s1Thumb);
            mov.setThumbnail(s1Thumb);

            repository.save(mov);
            var movie = new Movie();
            movie.setTitle("The Dark Knight Returns");
            movie.setPublishDate(LocalDate.of(2012, 12, 12));
            movie.setSummary("This is another Summary of The Dark knight returns");
            movie.setCategory(cat);
            var s2Stream = new File();

            s2Stream.setName("Sample 2");
            s2Stream.setMimeType("video/mov");
            store.setContent(s2Stream, new FileInputStream("./test-data/sample2.mov"));
            fileRepo.save(s2Stream);
            mov.setStream(s2Stream);
            File s2Thumb = new File();
            s2Thumb.setName("Sample 2 Thumb");
            s2Thumb.setMimeType("image/jpg");
            store.setContent(s2Thumb, new FileInputStream("./test-data/sample2.jpg"));
            mov.setThumbnail(s2Thumb);
            movie.setAuthor(auth);
            fileRepo.save(s2Thumb);
            movie.setStream(s2Stream);
            movie.setThumbnail(s2Thumb);
            repository.save(movie);
        };
    }
}
