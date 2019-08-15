package com.twu.biblioteca;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;



public class BibliotecaApp {
    ArrayList<Book> books;

    public BibliotecaApp() {
        books = new ArrayList<Book>(Arrays.asList(new Book("book1"),new Book("book2"), new Book("book3"), new Book("book4")));
    }



    public void printWelcome(PrintStream printStream) {
        System.out.print("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bagalore!");
    }

    public void printBookList() {
        for (Book book: books) {
            System.out.print(book.name);
            System.out.print(", ");
        }
        System.out.print("that's all the books we have!");
    }
}
