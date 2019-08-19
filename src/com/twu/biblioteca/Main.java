package com.twu.biblioteca;

import jdk.internal.util.xml.impl.Input;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        ArrayList<Book> books = new ArrayList<Book>(Arrays.asList(new Book("book1", "by me", 1982),
                new Book("book2", "by someone else", 1888),
                new Book("book3", "by my mom", 1969),
                new Book("book4", "by my sister", 1990)));;
        ;

        PrintStream p = new PrintStream(System.out);
        BibliotecaApp ap = new BibliotecaApp(books, p, new InputTaker(System.in, p));
        ap.printWelcome(System.out);
        ap.beginMenu();
    }

}
