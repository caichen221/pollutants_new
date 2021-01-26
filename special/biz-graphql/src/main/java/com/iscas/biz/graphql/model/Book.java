package com.iscas.biz.graphql.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
 
 
@Data
@EqualsAndHashCode(callSuper = false)
public class Book {
    private String title;
 
    private String isbn;
 
    private int pageCount;
 
    private long authorId;
}