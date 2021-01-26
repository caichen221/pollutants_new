package com.iscas.biz.graphql.resolve;
 
import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import com.iscas.biz.graphql.model.Author;
import com.iscas.biz.graphql.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * @author hanliwei
 * @create 2019-02-12 18:00
 */
@Component
@AllArgsConstructor
public class Query implements GraphQLQueryResolver {

 
    public Author findAuthorById(Long id) {
        return null;
    }
 
    public List<Author> findAllAuthors() {
        return null;
    }
 
    public Long countAuthors() {
        return null;
    }
 
    public List<Book> findAllBooks() {
        return null;
    }
 
    public Long countBooks() {
        return null;
    }
}