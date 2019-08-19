package com.twu.biblioteca;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class InputTaker {
    private final Scanner scanner;
    private final PrintStream out;

    public InputTaker (InputStream in, PrintStream out) {
        scanner = new Scanner(in);
        this.out = out;
    }

    public String ask(String message) {
        out.println(message);
        String s = scanner.nextLine();
        return s;
    }
}
