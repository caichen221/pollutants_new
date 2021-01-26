package com.iscas.biz.graphql.resolve;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.iscas.biz.graphql.model.Author;
import com.iscas.biz.graphql.model.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author hanliwei
 * @create 2019-02-12 17:33
 */
@Component
@AllArgsConstructor
public class AuthorResolver implements GraphQLResolver<Author> {
 
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
//    private BookRepo bookRepo;
 
    public String getCreatedTime(Author author) {

        return null;
    }
 
    public List<Book> getBooks(Author author) {
        return null;
    }
}