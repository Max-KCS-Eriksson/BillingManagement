package com.maxeriksson.BillingManagement;

import com.maxeriksson.BillingManagement.model.Customer;
import com.maxeriksson.BillingManagement.model.SocialSecurityNumber;
import com.maxeriksson.BillingManagement.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.*;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/** CommandLineRunnerImpl */
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private CommandLineInput in = new CommandLineInput();

    @Autowired CustomerRepository customerRepository;

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
                case 1 -> {
                    System.out.println();
                    handleBilling();
                }
                case 2 -> {
                    System.out.println();
                    handleCustomers();
                }
                case 3 -> {
                    System.out.println();
                    handleServices();
                }
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
        boolean isHandlingCustomers = true;
        String[] menuChoices = {
            "Show all Customers in Registry",
            "Add Customer to Registry, or Update existing Customer",
            "Delete Customer from Registry",
            "Main Menu"
        };
        while (isHandlingCustomers) {
            printHumanReadableMenuChoiceIndexes(menuChoices);
            switch (pickListIndex(menuChoices)) {
                case 1 -> {
                    printAllCustomers();
                }
                case 2 -> {
                    Optional<Customer> customer = createCustomer();
                    if (customer.isPresent()) {
                        customerRepository.save(customer.get());
                    }
                }
                case 3 -> deleteCustomer();

                case 4 -> isHandlingCustomers = false;
            }
        }
    }

    private void handleServices() {
        System.out.println("WARN: `handleServices()` NOT IMPLEMENTED"); // TODO: IMPLEMENT
    }

    // Handle Customers

    private void printAllCustomers() {
        System.out.println("All Customers in Registry:");
        for (Customer customer : customerRepository.findAll()) {
            System.out.println("  " + customer);
        }
    }

    private Optional<Customer> createCustomer() {
        SocialSecurityNumber socialSecurityNumber;
        try {
            socialSecurityNumber = createUniqueSocialSecurityNumber().get();
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }

        String firstName = toInitialUpperCase(in.inputString("First name"));
        String lastName = toInitialUpperCase(in.inputString("Last name"));
        String address = "";
        for (String word : in.inputString("Address").split(" ")) {
            address += toInitialUpperCase(word) + " ";
        }

        return Optional.of(new Customer(socialSecurityNumber, firstName, lastName, address));
    }

    private void deleteCustomer() {
        Optional<SocialSecurityNumber> socialSecurityNumber = createExistingSocialSecurityNumber();
        Optional<Customer> customer = customerRepository.findById(socialSecurityNumber.get());
        if (customer.isPresent()) {
            System.out.println("Customer found in Registry:\n  " + customer.get());
            if (in.inputConfirmation("Delete"))
                customerRepository.deleteById(socialSecurityNumber.get());
        }
    }

    private Optional<SocialSecurityNumber> createUniqueSocialSecurityNumber() {
        SocialSecurityNumber socialSecurityNumber = null;

        boolean isUniqueId = false;
        while (!isUniqueId) {
            LocalDate dateOfBirth = createLocalDate();
            int idLastFour = createSocialSecurityNumberLastFourDigit();
            socialSecurityNumber = new SocialSecurityNumber(dateOfBirth, idLastFour);

            isUniqueId = !customerRepository.existsById(socialSecurityNumber);
            if (!isUniqueId) {
                System.out.println(
                        "ID number already exists in the registry:\n  " + socialSecurityNumber);
                if (in.inputConfirmation("Update existing customer details?\n")) {
                    break;
                } else {
                    return Optional.empty();
                }
            }
        }
        return Optional.of(socialSecurityNumber);
    }

    private Optional<SocialSecurityNumber> createExistingSocialSecurityNumber() {
        SocialSecurityNumber socialSecurityNumber = null;

        boolean isUniqueId = false;
        while (!isUniqueId) {
            LocalDate dateOfBirth = createLocalDate();
            int idLastFour = createSocialSecurityNumberLastFourDigit();
            socialSecurityNumber = new SocialSecurityNumber(dateOfBirth, idLastFour);

            isUniqueId = customerRepository.existsById(socialSecurityNumber);
            if (!isUniqueId) {
                System.out.println(
                        "ID number doesn't exists in the registry:\n  " + socialSecurityNumber);
                if (!in.inputConfirmation("Try again?\n")) {
                    return Optional.empty();
                }
            }
        }
        return Optional.of(socialSecurityNumber);
    }

    private LocalDate createLocalDate() {
        LocalDate date = LocalDate.MAX;
        boolean isDateValid = false;
        while (!isDateValid) {
            String dateOfBirthInput =
                    in.inputString("Birth date (yyyyMMdd)")
                            .replace("-", "")
                            .replace("/", "")
                            .replace(" ", "");
            try {
                date = LocalDate.parse(dateOfBirthInput, DateTimeFormatter.ofPattern("yyyyMMdd"));
                isDateValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. Try again.");
            }
        }
        return date;
    }

    private int createSocialSecurityNumberLastFourDigit() {
        int idLastFour = -1;
        while (idLastFour < 0 || idLastFour > 9999) {
            idLastFour = in.inputInt("ID last four");
        }
        return idLastFour;
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
