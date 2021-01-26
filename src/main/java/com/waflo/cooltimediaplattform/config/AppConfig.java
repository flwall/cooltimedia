package com.waflo.cooltimediaplattform.config;

import org.slf4j.LoggerFactory;
import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Configuration
@EnableFilesystemStores
public class AppConfig implements WebMvcConfigurer {

    @Bean
    File filesystemRoot() {
        try {
            return Files.createDirectories(Path.of("./uploads")).toFile();
        } catch (IOException ioe) {
        }
        return null;
    }

    @Bean
    FileSystemResourceLoader fileSystemResourceLoader() {
        return new FileSystemResourceLoader(filesystemRoot().getAbsolutePath());
    }

}
