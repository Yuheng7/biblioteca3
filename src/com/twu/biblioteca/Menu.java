package com.twu.biblioteca;

import javafx.util.Pair;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;


// Maybe I should take all the instantiations of input stream and make one input stream outside
public class Menu {
    ArrayList<Book> books;
    ArrayList<Book> checked_out;
    ArrayList<String> options;
    PrintStream ps;

    public Menu(ArrayList<Book> books, PrintStream ps) {
        this.books = books;
        this.checked_out = new ArrayList<Book>();
        this.ps = ps;
        this.options = new ArrayList<String>(Arrays.asList(("(A) View list of books/authors/publications years"),
                ("(B) Quit"),
                ("(C) Checkout books"),
                ("(D) Return a book")));
    }

    public void printBookListInDepth() {
        for (Book book: books) {
            this.ps.println(book.toStringDetailed());
        }
    }
    public void printBookList() {
        for (Book book: books) {
            this.ps.print(book.getName());
            this.ps.print(", ");
        }
        this.ps.print("that's all the books we have!");
    }

    public void printOptions() {
        for (String option : options) {
            this.ps.println(option);
        }
    }


    public String getOptionFromUser(InputTaker asker) {
        String input = asker.ask("Which option would you like to select?");
        this.ps.print("\n");
        if (input.equals("A") || input.equals("B") || input.equals("C") || input.equals("D")) {
            return input;
        }
        while (true) {
            input = asker.ask("Wrong option, please retype answer");
            if (input.equals("A") || input.equals("B") || input.equals("C") || input.equals("D")) {
                return input;
            }
        }
    }

    public Pair<Boolean, String> getBookFromUser(InputTaker asker) {
        String name = asker.ask("What is the name of the book you would like to checkout?");
        String author = asker.ask("What is the author of the book you would like to checkout?");
        String pub_year = asker.ask("What is the publication year of the book you would like to checkout?");
        boolean valid = false;
        while (valid == false) {
            try {
                int pub_year_int = Integer.parseInt(pub_year);
                valid = true;
            } catch (NumberFormatException e) {
                this.ps.println("Invalid year");
                pub_year = asker.ask("What is the publication year of the book you would like to checkout?");
            }
        }
        String detailed_string = name + " | " + author + " | " + pub_year;
        for (Book book : books) {
            if (book.toStringDetailed().equals(detailed_string)) {
                this.books.remove(book);
                this.checked_out.add(book);
                return new Pair<Boolean, String>(true, book.toStringDetailed());
            }
        }
        this.ps.println("Sorry, that's not a book in our system");
        return new Pair<Boolean, String>(false, "");


    }

    public boolean carryOutUserOrder() {
        String input = getOptionFromUser(new InputTaker(System.in, this.ps));
        if (input.equals("A")) {
            this.printBookListInDepth();
        }
        else if (input.equals("B")) {
            this.ps.print("Terminating Program. Have a great day!");
            return false;
        }
        else if (input.equals("C")) {
            this.checkOutBook(new InputTaker(System.in, this.ps), 0);
        }
        else if (input.equals("D")) {
            this.checkOutBook(new InputTaker(System.in, this.ps), 1);
        }
        return true;
    }

    public ArrayList<Book> returnUpdatedBookList() {
        return books;
    }

    public void checkOutBook(InputTaker asker, int in_out) {
        boolean valid = false;
        while (valid == false) {
            Pair<Boolean, String> pair = new Pair<Boolean, String>(false, "");
            if (in_out == 0) {
                pair  = getBookFromUser(new InputTaker(System.in, this.ps));
            } else if (in_out == 1){
                pair = getBookReturnFromUser(new InputTaker(System.in, this.ps));
            }
            valid = pair.getKey();
            String details = pair.getValue();
            if (valid == false) {
                String input = asker.ask("Would you like to try again (y/n)");
                if (input.equals("n")) {
                    valid = true;
                    this.ps.println("Please try again later");
                } else if (input.equals("y")) {
                    this.ps.println("Ok let's try again");
                } else {
                    this.ps.println("Invalid option, returning to main menu");
                }
            }
            if (valid == true) {
                if (in_out == 0) {
                    String output = "Checking out " + details;
                    this.ps.println(output);
                } else if (in_out == 1) {
                    String output = "Returning " + details;
                    this.ps.println(output);
                }

            }

        }
    }

    public Pair<Boolean, String> getBookReturnFromUser(InputTaker asker) {
        String name = asker.ask("What is the name of the book you would like to return?");
        String author = asker.ask("What is the author of the book you would like to return?");
        String pub_year = asker.ask("What is the publication year of the book you would like to return?");
        boolean valid = false;
        while (valid == false) {
            try {
                int pub_year_int = Integer.parseInt(pub_year);
                valid = true;
            } catch (NumberFormatException e) {
                this.ps.println("Invalid year");
                pub_year = asker.ask("What is the publication year of the book you would like to return?");
            }
        }
        String detailed_string = name + " | " + author + " | " + pub_year;
        for (Book book : this.checked_out) {
            if (book.toStringDetailed().equals(detailed_string)) {
                this.checked_out.remove(book);
                this.books.add(book);
                return new Pair<Boolean, String>(true, book.toStringDetailed());
            }
        }
        this.ps.println("Sorry, that's not a valid book to return!");
        return new Pair<Boolean, String>(false, "");

    }
}
