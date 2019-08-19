package com.twu.biblioteca;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;



public class BibliotecaApp {
    Menu menu;
    ArrayList<Book> books;
    private PrintStream ps;
    InputTaker asker;

    public BibliotecaApp(ArrayList<Book> books, PrintStream ps, InputTaker asker) {
        this.books = books;
        this.menu = new Menu(books, ps);
        this.ps = ps;
        this.asker = asker;
    }



    public void printWelcome(PrintStream printStream) {
        this.ps.print("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bagalore!\n");
    }

    public void beginMenu() {
        boolean running = true;
        while (running == true) {
            this.menu.printOptions();
            running = this.menu.carryOutUserOrder();
        }
        this.books = this.menu.returnUpdatedBookList();
    }



}
