package com.iscas.biz.graphql.resolve;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.iscas.biz.graphql.emum.CountryEnum;
import com.iscas.biz.graphql.entity.Author;
import com.iscas.biz.graphql.entity.Book;
import com.iscas.biz.graphql.repo.AuthorRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BookResolver implements GraphQLResolver<Book> {

    private AuthorRepo authorRepo;

    public Author getAuthor(Book book) {
        return authorRepo.findAuthorById(book.getAuthorId());
    }

    public String getMoney(Book book, CountryEnum country) {
        if (country == CountryEnum.CHINA) {
            return "¥:" + book.getPrice() * 6;
        } else if (country == CountryEnum.USA) {
            return "$:" + book.getPrice();
        } else {
            return "free";
        }
    }
}
