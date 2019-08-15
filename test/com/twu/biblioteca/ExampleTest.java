package com.twu.biblioteca;


import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ExampleTest {
    BibliotecaApp ba = new BibliotecaApp();
    @Test
    public void testWelcomeMessage() {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        ba.printWelcome(System.out);
        assertThat(os.toString(), is(equalTo("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bagalore!")));

    }

    @Test
    public void testBookList() {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        ba.printBookList();
        assertThat(os.toString(), is(equalTo("book1, book2, book3, book4, that's all the books we have!")));
    }
}
