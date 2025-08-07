package com.nitesh.BookmarkManager.controller;

import com.nitesh.BookmarkManager.model.Bookmark;
import com.nitesh.BookmarkManager.repository.BookmarkRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class BookmarkControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Value("${local.server.port}")
    private int port;

    private final String BASE_URL = "http://localhost:";

    @BeforeEach
    public void setUp() {
        bookmarkRepository.deleteAll();

        Bookmark bookmark1 = Bookmark.builder()
                .title("Spring Documentation")
                .url("https://spring.io/docs")
                .description("Official Spring Framework documentation.")
                .tags("spring,java,backend")
                .build();

        Bookmark bookmark2 = Bookmark.builder()
                .title("Baeldung")
                .url("https://www.baeldung.com")
                .description("Java tutorials and articles.")
                .tags("java,spring,tutorials")
                .build();

        bookmarkRepository.save(bookmark1);
        bookmarkRepository.save(bookmark2);
    }

    @Test
    public void test_GetAllBookmarks_ReturnsListOfBookmarks() {
        ResponseEntity<List<Bookmark>> response = restTemplate.exchange(
                BASE_URL + port + "/api/bookmarks",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Bookmark>>() {}
        );

        System.out.println("################################");
        System.out.println(response.getBody());
        System.out.println("################################");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());

        Bookmark first = response.getBody().get(0);
        assertNotNull(first.getId());
        assertNotNull(first.getTitle());
        assertNotNull(first.getUrl());
        assertNotNull(first.getCreatedAt());

        // Optional: Check one of the descriptions
        assertTrue(
                response.getBody().stream().anyMatch(b -> b.getTitle().equals("Baeldung")),
                "Expected bookmark with title 'Baeldung' not found"
        );
    }
}
