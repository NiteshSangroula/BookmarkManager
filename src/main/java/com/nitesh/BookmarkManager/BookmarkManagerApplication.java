package com.nitesh.BookmarkManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BookmarkManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookmarkManagerApplication.class, args);
	}

    @GetMapping("/")
    public String home() {
        return "Bookmark Manager API is running!";
    }

}
