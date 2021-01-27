package com.iscas.biz.graphql.resolve;


import com.coxautodev.graphql.tools.GraphQLResolver;
import com.iscas.biz.graphql.entity.Author;
import com.iscas.biz.graphql.entity.Book;
import com.iscas.biz.graphql.repo.BookRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;


@Component
@AllArgsConstructor
public class AuthorResolver implements GraphQLResolver<Author> {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private BookRepo bookRepo;

    public String getCreatedTime(Author author) {
        return sdf.format(author.getCreatedTime());
    }

    public List<Book> getBooks(Author author) {
        return bookRepo.findByAuthorId(author.getId());
    }
}
