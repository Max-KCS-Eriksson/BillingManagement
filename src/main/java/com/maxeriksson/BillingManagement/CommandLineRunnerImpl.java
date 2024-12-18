package com.maxeriksson.BillingManagement;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/** CommandLineRunnerImpl */
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private CommandLineInput in = new CommandLineInput();

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Welcome to the Billing Management System\n");

        boolean isRunning = true;
        String[] menuChoices = {
            "Handle Billing",
            "Handle Customer Registry",
            "Handle Service Registry",
            "Exit the Billing Management System"
        };
        while (isRunning) {
            printHumanReadableMenuChoiceIndexes(menuChoices);
            switch (pickListIndex(menuChoices)) {
                case 1 -> handleBilling();
                case 2 -> handleCustomers();
                case 3 -> handleServices();
                case 4 -> isRunning = false;
            }
        }

        System.out.println("Thank you for using the Billing Management System\nGoodbye");
        in.close();
    }

    private void handleBilling() {
        System.out.println("WARN: `handleBilling()` NOT IMPLEMENTED"); // TODO: IMPLEMENT
    }

    private void handleCustomers() {
        System.out.println("WARN: `handleCustomers()` NOT IMPLEMENTED"); // TODO: IMPLEMENT
    }

    private void handleServices() {
        System.out.println("WARN: `handleServices()` NOT IMPLEMENTED"); // TODO: IMPLEMENT
    }

    // Helpers

    private void printHumanReadableMenuChoiceIndexes(String[] menu) {
        for (int i = 0; i < menu.length; i++) {
            System.out.println(String.format("%2d", (i + 1)) + ".  " + menu[i]);
        }
    }

    private <T> int pickListIndex(T[] arr) {
        return pickListIndex(Arrays.asList(arr));
    }

    private <T> int pickListIndex(List<T> list) {
        while (true) {
            int index = in.inputInt("Choice") - 1;
            if (index >= 0 && index < list.size()) {
                return index + 1;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private <T> T selectFromList(T[] arr) {
        return selectFromList(Arrays.asList(arr));
    }

    private <T> T selectFromList(List<T> list) {
        while (true) {
            try {
                return list.get(in.inputInt("Choice") - 1);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private String toInitialUpperCase(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }
}
