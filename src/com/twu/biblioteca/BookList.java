package com.twu.biblioteca;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.ArrayList;
import java.util.Arrays;

public class BookList {
    ArrayList<Book> books;

    public BookList(ArrayList<Book> books) {
        this.books = books;
    }
    public BookList() {
        this.books = books = new ArrayList<Book>(Arrays.asList(new Book("book1", "by me", 1982),new Book("book2", "by someone else", 1888), new Book("book3", "by my mom", 1969), new Book("book4", "by my sister", 1990)));;
    }

    public void addBook(Book book) {
        //Implement it
    }

    public void removeBook(Book book) {
        //Implement it
    }
}
