package com.nitesh.BookmarkManager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.nitesh.BookmarkManager.repository.BookmarkRepository;
import com.nitesh.BookmarkManager.model.Bookmark;

@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    public BookmarkService(BookmarkRepository bookmarkRepository) {
        this.bookmarkRepository = bookmarkRepository;
    }

    public List<Bookmark> getAllBookmarks() {
        return bookmarkRepository.findAll();
    }

    public Optional<Bookmark> getBookmarkById(Long id) {
        return bookmarkRepository.findById(id);
    }

    public Bookmark createBookmark(Bookmark bookmark) {
        return bookmarkRepository.save(bookmark);
    }

    public Bookmark updateBookmark(Long id, Bookmark updatedBookmark) {
        validateBookmark(updatedBookmark);
        return bookmarkRepository.findById(id)
        .map(existing -> {
            existing.setTitle(updatedBookmark.getTitle());
            existing.setUrl(updatedBookmark.getUrl());
            existing.setDescription(updatedBookmark.getDescription());
            existing.setTags(updatedBookmark.getTags());
            return bookmarkRepository.save(existing);
        })
        .orElseThrow(() -> new RuntimeException("Bookmark not found"));
    }

    public void deleteBookmark(Long id) {
        if (!bookmarkRepository.existsById(id)) {
            throw new RuntimeException("Bookmark not found!");
        }
        bookmarkRepository.deleteById(id);
    }

    private void validateBookmark(Bookmark bookmark) {
        if ( bookmark.getTitle() == null || bookmark.getTitle().isBlank()) {
            throw new RuntimeException("Title is required");
        }
        if (bookmark.getUrl() == null || bookmark.getUrl().isBlank()) {
            throw new RuntimeException("URL is required");
        }
    }


}
