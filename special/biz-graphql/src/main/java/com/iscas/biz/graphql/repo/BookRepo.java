package com.iscas.biz.graphql.repo;


import com.iscas.biz.graphql.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepo extends JpaRepository<Book,Long> {
    List<Book> findByAuthorId(Long id);

    Book findBookById(Long id);
}
