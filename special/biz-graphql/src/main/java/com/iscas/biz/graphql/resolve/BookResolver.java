package com.iscas.biz.graphql.resolve;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.iscas.biz.graphql.model.Author;
import com.iscas.biz.graphql.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookResolver implements GraphQLResolver<Book> {

    public Author getAuthor(Book book) {
        return null;
    }
}