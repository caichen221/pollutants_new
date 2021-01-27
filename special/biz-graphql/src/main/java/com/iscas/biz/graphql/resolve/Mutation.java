package com.iscas.biz.graphql.resolve;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.iscas.biz.graphql.entity.Author;
import com.iscas.biz.graphql.entity.Book;
import com.iscas.biz.graphql.entity.User;
import com.iscas.biz.graphql.model.*;
import com.iscas.biz.graphql.repo.AuthorRepo;
import com.iscas.biz.graphql.repo.BookRepo;
import com.iscas.biz.graphql.repo.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
@AllArgsConstructor
public class Mutation implements GraphQLMutationResolver {
    private AuthorRepo authorRepo;
    private BookRepo bookRepo;

    private UserRepo userRepo;

    public Author newAuthor(String firstName, String lastName) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);
        return authorRepo.save(author);
    }

    public Book newBook(String title, String isbn, int pageCount, Long authorId) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPageCount(pageCount);
        book.setAuthorId(authorId);
        return bookRepo.save(book);
    }


    public Book saveBook(BookInput input) {
        Book book = new Book();
        book.setTitle(input.getTitle());
        book.setIsbn(input.getIsbn());
        book.setPageCount(input.getPageCount());
        book.setAuthorId(input.getAuthorId());
        return bookRepo.save(book);
    }

    public Boolean deleteBook(Long id) {
        bookRepo.deleteById(id);
        return true;
    }

    public Book updateBookPageCount(int pageCount,long id) {
        Book book = bookRepo.findBookById(id);
        book.setPageCount(pageCount);
        return bookRepo.save(book);
    }


    public CreateUserResult createUser(String name, AuthData authData) {
        if (userRepo.findUserByName(name) != null) {
            return new ErrorContainer(Stream.of("The user already exists.").collect(Collectors.toList()));
        } else {
            User user = new User();
            user.setPwd(authData.getPwd());
            user.setEmail(authData.getEmail());
            user.setName(name);
            return new CreatedUser(userRepo.save(user));
        }
    }

    public LoginResult login(AuthData authData) {
        String email = authData.getEmail();
        User user = userRepo.findUserByEmail(email);
        if ( user == null) {
            return new ErrorContainer(Stream.of("Unregistered email.").collect(Collectors.toList()));
        } else if (userRepo.findUserByEmailAndPwd(email,authData.getPwd()) == null) {
            return new ErrorContainer(Stream.of("Wrong password.").collect(Collectors.toList()));
        } else {
           return new LoginPayload(user.getId(),user);
        }
    }
}
