package com.nitesh.BookmarkManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nitesh.BookmarkManager.model.Bookmark;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long>{

    
}
