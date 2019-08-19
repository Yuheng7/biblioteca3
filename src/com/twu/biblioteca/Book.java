package com.twu.biblioteca;

public class Book {
    private String name;
    private String author;
    private int pub_year;


    public Book(String name, String author, int pub_year) {
        this.name = name;
        this.author = author;
        this.pub_year = pub_year;
    }

    public String toStringDetailed() {

        String returnString = this.name + " | " + this.author + " | " + Integer.toString(this.pub_year);
        return returnString;
    }

    public String getName() {
        return this.name;
    }
}
