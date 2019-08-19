package com.twu.biblioteca;


import javafx.util.Pair;
import jdk.internal.util.xml.impl.Input;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import static org.mockito.Mockito.*;


public class ExampleTest {
    ArrayList<Book> books = new ArrayList<Book>(Arrays.asList(new Book("book1", "by me", 1982),
            new Book("book2", "by someone else", 1888),
            new Book("book3", "by my mom", 1969),
            new Book("book4", "by my sister", 1990)));;
    PrintStream p;
    BibliotecaApp ap;
    Menu menu;
    OutputStream os;

    @Before
    public void setUp() throws Exception {
        this.os = new ByteArrayOutputStream();
        this.p = new PrintStream(this.os);
        this.ap = new BibliotecaApp(books, p, new InputTaker(System.in, p));
        this.menu = new Menu(books, p);
    }

    @Test
    public void testWelcomeMessage() {

        this.ap.printWelcome(this.p);
        assertThat(this.os.toString(), is(equalTo("Welcome to Biblioteca. Your one-stop-shop for great book titles in Bagalore!\n")));

    }

    @Test
    public void testBookList() {
        menu.printBookList();
        assertThat(this.os.toString(), is(equalTo("book1, book2, book3, book4, that's all the books we have!")));
    }

    @Test
    public void testBookListInDepth() {
        menu.printBookListInDepth();
        assertThat(this.os.toString(), is(equalTo("book1 | by me | 1982\nbook2 | by someone else | 1888\nbook3 | by my mom | 1969\nbook4 | by my sister | 1990\n")));
    }

    @Test
    public void viewMenu() {
        menu.printOptions();
        assertThat(this.os.toString(), is(equalTo("(A) View list of books/authors/publications years\n(B) Quit\n(C) Checkout books\n(D) Return a book\n")));
    }

    @Test
    public void getsCorrectOption() throws Exception {
        InputTaker asker = mock(InputTaker.class);
        when(asker.ask(anyString())).thenReturn("A");
        assertEquals(menu.getOptionFromUser(asker), "A");
    }

    @Test
    public void checkInvalidOption() throws Exception {
        InputTaker asker = mock(InputTaker.class);
        when(asker.ask("Which option would you like to select?")).thenReturn("Definitely not A");
        when(asker.ask("Wrong option, please retype answer")).thenReturn("A");
        menu.getOptionFromUser(asker);
        verify(asker).ask("Wrong option, please retype answer");
    }

    ///still need to fix im pretty sure --> how to check if program terminated with exit code 0
//    @Test
//    public void testEndOfProgram() {
//        InputTaker asker = mock(InputTaker.class);
//        when(asker.ask("Which option would you like to select?")).thenReturn("B");
//        menu.carryOutUserOrder();
//        OutputStream os = new ByteArrayOutputStream();
//        PrintStream ps = new PrintStream(os);
//        System.setOut(ps);
//        assertThat(os.toString(), is(equalTo("Terminating Program. Have a great day!")));
//    }

    @Test
    public void testCheckOutBook() {
        InputTaker asker = mock(InputTaker.class);

        when(asker.ask("What is the name of the book you would like to checkout?")).thenReturn("book1");
        when(asker.ask("What is the author of the book you would like to checkout?")).thenReturn("by me");
        when(asker.ask("What is the publication year of the book you would like to checkout?")).thenReturn("1982");
        Pair<Boolean, String> pair = menu.getBookFromUser(asker);
        String message = "Checking out: " + pair.getValue();

        assertThat(message, is(equalTo("Checking out: book1 | by me | 1982")));
    }

//   Still can't really figure out how to test for this. like why isn't that reading it. Also how can you test for input and output in one.
    @Test
    public void testCheckOutBookInvalid() {
        InputTaker asker = mock(InputTaker.class);

        when(asker.ask("What is the name of the book you would like to checkout?")).thenReturn("book1");
        when(asker.ask("What is the author of the book you would like to checkout?")).thenReturn("by whomst");
        when(asker.ask("What is the publication year of the book you would like to checkout?")).thenReturn("1982");
        Pair<Boolean, String> pair = menu.getBookFromUser(asker);
//        System.setOut(ps);
        assertThat(this.os.toString(), is("Sorry, that's not a book in our system\n"));
//        verify(asker).ask("Sorry, that's not a book in our system");
    }

    /// Need to figure out why objects are different --> clearly the book is being taken out though
    @Test
    public void testCheckoutBookNotInList() {
        InputTaker asker = mock(InputTaker.class);
        when(asker.ask("What is the name of the book you would like to checkout?")).thenReturn("book1");
        when(asker.ask("What is the author of the book you would like to checkout?")).thenReturn("by me");
        when(asker.ask("What is the publication year of the book you would like to checkout?")).thenReturn("1982");
        menu.getBookFromUser(asker);
        // how do you print stuff out in testing?
//        for (Book book : books) {
//            this.ps.println(book.toStringDetailed());
//        }

        String booksDetailed = "";
        for (Book book : menu.books) {
            booksDetailed = booksDetailed + book.toStringDetailed();
        }
        assertThat(booksDetailed, is(equalTo("book2 | by someone else | 1888book3 | by my mom | 1969book4 | by my sister | 1990")));

    }

    @Test
    public void testReturnBook() {
        InputTaker asker = mock(InputTaker.class);
        when(asker.ask("What is the name of the book you would like to return?")).thenReturn("book1");
        when(asker.ask("What is the author of the book you would like to return?")).thenReturn("by me");
        when(asker.ask("What is the publication year of the book you would like to return?")).thenReturn("1982");
        menu.getBookReturnFromUser(asker);
        //really this should be tested as the books and their details printed out, but, will do this in a bit (same with the one below this)
        String booksDetailed = "";
        for (Book book : menu.books) {
            booksDetailed = booksDetailed + book.toStringDetailed();
        }
        assertThat(booksDetailed, is(equalTo("book1 | by me | 1982book2 | by someone else | 1888book3 | by my mom | 1969book4 | by my sister | 1990")));
    }

    @Test
    public void testReturnOutBookInvalid() {
        InputTaker asker = mock(InputTaker.class);

        when(asker.ask("What is the name of the book you would like to return?")).thenReturn("book2");
        when(asker.ask("What is the author of the book you would like to return?")).thenReturn("by someone else");
        when(asker.ask("What is the publication year of the book you would like to return?")).thenReturn("1888");
        Pair<Boolean, String> pair = menu.getBookReturnFromUser(asker);

        assertThat(this.os.toString(), is("Sorry, that's not a valid book to return!\n"));

    }
    @Test
    public void testReturnBookMessage() {
        InputTaker asker = mock(InputTaker.class);

        //checking out book 1
        when(asker.ask("What is the name of the book you would like to checkout?")).thenReturn("book1");
        when(asker.ask("What is the author of the book you would like to checkout?")).thenReturn("by me");
        when(asker.ask("What is the publication year of the book you would like to checkout?")).thenReturn("1982");
        menu.getBookFromUser(asker);

        //returning book1
        when(asker.ask("What is the name of the book you would like to return?")).thenReturn("book1");
        when(asker.ask("What is the author of the book you would like to return?")).thenReturn("by me");
        when(asker.ask("What is the publication year of the book you would like to return?")).thenReturn("1982");
        Pair<Boolean, String> pair = menu.getBookReturnFromUser(asker);
        String message = "Returning: " + pair.getValue();

        assertThat(message, is(equalTo("Returning: book1 | by me | 1982")));
    }
}

