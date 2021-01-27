package com.iscas.biz.graphql.repo;


import com.iscas.biz.graphql.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author,Long> {

    Author findAuthorById(Long id);
}
