package com.iscas.biz.graphql.resolve;
 
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.iscas.biz.graphql.model.Author;
import com.iscas.biz.graphql.model.Book;
import com.iscas.biz.graphql.model.BookInput;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
 

@Component
@AllArgsConstructor
public class Mutation implements GraphQLMutationResolver {

    public Author newAuthor(String firstName, String lastName) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        return null;
    }
 
    public Book newBook(String title, String isbn, int pageCount, Long authorId) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPageCount(pageCount);
        book.setAuthorId(authorId);
        return null;
    }
 
 
    public Book saveBook(BookInput input) {
        Book book = new Book();
        book.setTitle(input.getTitle());
        book.setIsbn(input.getIsbn());
        book.setPageCount(input.getPageCount());
        book.setAuthorId(input.getAuthorId());
        return null;
    }
 
    public Boolean deleteBook(Long id) {

        return true;
    }
 
    public Book updateBookPageCount(int pageCount,long id) {
        return null;
    }
 
}