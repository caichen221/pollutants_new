package com.iscas.biz.graphql.model;

import lombok.Data;


@Data
public class BookInput {
    private String title;

    private String isbn;

    private int pageCount;

    private long authorId;
}
