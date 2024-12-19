package com.maxeriksson.BillingManagement;

import java.util.Scanner;

/** CommandLineInput */
public class CommandLineInput {

    private final Scanner SCANNER;

    public CommandLineInput() {
        SCANNER = new Scanner(System.in);
    }

    public String inputString(String prompt) {
        String input = "";
        while (input.isBlank()) {
            System.out.print(prompt + " > ");

            input = SCANNER.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Invalid input. Try again.");
            }
        }
        return input;
    }

    public int inputInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(inputString(prompt));
            } catch (NumberFormatException e) {
                System.out.println("Invalid input - only accept integers. Try again.");
            }
        }
    }

    public double inputDouble(String prompt) {
        while (true) {
            try {
                String localeCorrection = inputString(prompt).replace(",", ".");
                return Double.parseDouble(localeCorrection);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input - only accept numerics. Try again.");
            }
        }
    }

    public void close() {
        SCANNER.close();
    }
}
