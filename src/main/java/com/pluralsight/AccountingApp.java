package com.pluralsight;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountingApp
{
    // easter egg 1 color
    public static final String RED = "\u001B[31m";
    // resets terminal text color so everything isn't RED
    public static final String RESET = "\u001B[0m";


    // variables called here to be used throughout the program
    static Scanner userInput = new Scanner(System.in);
    static ArrayList<TransactionsInfo> transactions;



    static void main()
    {
        transactions = loadTransactions();
        displayHomeScreen();
    }





    static void displayHomeScreen()
    {
        System.out.println();
        System.out.println("Accounting Ledger Application");
        System.out.println("---------------------------------");
        System.out.println("D) Add Deposit (Credit)");
        System.out.println("P) Make A Payment (Debit)");
        System.out.println("L) Ledger Display");
        System.out.println("X) Exit");
        System.out.println();
        System.out.print("Make your selection here: ");
        String selection = userInput.nextLine().toUpperCase().strip();

        System.out.println();

        switch (selection)
        {
            case "D":
                displayDepositScreen();
                break;

            case "P":
                displayPaymentScreen();
                break;

            case "L":
                displayLedgerScreen();
                break;

            case "X":
                System.out.println("See you next time");
                break;

            // easter egg
            case "SUS":
                String art = """
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⣤⣤⣤⣤⣤⣤⣤⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⢀⣴⣿⡿⠛⠉⠙⠛⠛⠛⠛⠻⢿⣿⣷⣤⡀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⠋⠀⠀⠀⠀⠀⠀⠀⢀⣀⣀⠈⢻⣿⣿⡄⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⣸⣿⡏⠀⠀⠀⣠⣶⣾⣿⣿⣿⠿⠿⠿⢿⣿⣿⣿⣄⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⣿⣿⠁⠀⠀⢰⣿⣿⣯⠁⠀⠀⠀⠀⠀⠀⠀⠈⠙⢿⣷⡄⠀
                ⠀⠀⣀⣤⣴⣶⣶⣿⡟⠀⠀⠀⢸⣿⣿⣿⣆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣷⠀
                ⠀⢰⣿⡟⠋⠉⣹⣿⡇⠀⠀⠀⠘⣿⣿⣿⣿⣷⣦⣤⣤⣤⣶⣶⣶⣶⣿⣿⣿⠀
                ⠀⢸⣿⡇⠀⠀⣿⣿⡇⠀⠀⠀⠀⠹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠃⠀
                ⠀⣸⣿⡇⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠉⠻⠿⣿⣿⣿⣿⡿⠿⠿⠛⢻⣿⡇⠀⠀
                ⠀⣿⣿⠁⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣧⠀⠀
                ⠀⣿⣿⠀⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⠀⠀
                ⠀⣿⣿⠀⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⣿⠀⠀
                ⠀⢿⣿⡆⠀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣿⡇⠀⠀
                ⠀⠸⣿⣧⡀⠀⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⠃⠀⠀
                ⠀⠀⠛⢿⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⣰⣿⣿⣷⣶⣶⣶⣶⠶⠀⢠⣿⣿⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⣿⣿⠀⠀⠀⠀⠀⣿⣿⡇⠀⣽⣿⡏⠁⠀⠀⢸⣿⡇⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⣿⣿⠀⠀⠀⠀⠀⣿⣿⡇⠀⢹⣿⡆⠀⠀⠀⣸⣿⠇⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⢿⣿⣦⣄⣀⣠⣴⣿⣿⠁⠀⠈⠻⣿⣿⣿⣿⡿⠏⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠈⠛⠻⠿⠿⠿⠿⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                """;
                System.out.println(RED + art + RESET);
                displayHomeScreen();
                break;

            default:
                System.out.println();
                System.out.println("Error, please try again");
                displayHomeScreen();
        }

    }





    static void displayDepositScreen()
    {
        System.out.println();
        System.out.println("Please Enter Your Deposit (Credit) Information Below");
        System.out.println("--------------------------------------------------------");


        // user inputs
        System.out.print("Please enter the amount you are depositing: ");
        double amountDeposited;

        // try catch so the app doesn't crash if user inputs a string or nothing when asked for $ amount
        try
        {
            amountDeposited = Double.parseDouble(userInput.nextLine());

            // makes it so user can't put a negative number inside something that should only be positive numbers
            if (amountDeposited < 0)
            {
                System.out.println();
                System.out.println("Invalid amount, please try again");
                displayDepositScreen();
                return;
            }
        }
        catch (Exception ex)
        {
            System.out.println();
            System.out.println("Invalid number, please try again");
            displayDepositScreen();
            return;
        }


        System.out.print("Please enter the date of the deposit using MM/DD/YYYY format: ");
        String userDateInput = userInput.nextLine();

        // fixes formatting of the date, makes it American standard
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate dateOfTransaction;

        // try catch so the app doesn't crash if user inputs a date in the wrong format
        try
        {
            dateOfTransaction = LocalDate.parse(userDateInput, formatter);
        }
        catch (Exception ex)
        {
            System.out.println();
            System.out.println("Invalid date format, please try again");
            displayDepositScreen();
            return;
        }


        System.out.print("Please enter a description: ");
        String enterDescription = userInput.nextLine().strip();


        System.out.print("Please enter the name of the vendor (whoever gave you the money): ");
        String vendorName = userInput.nextLine().strip();


        // add timestamp for hours,mins, and secs without user input
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeStamp = now.format(formatter2);

        // calls logTransactions method
        // plugs in user input variables above
        // the user inputs are recorded onto the .csv using logTransactions initial variables
        logTransactions(dateOfTransaction.format(formatter), timeStamp, enterDescription, vendorName, amountDeposited);

        System.out.println();
        System.out.println("Deposit Recorded");
        transactions = loadTransactions(); // reloads the arraylist/.csv so it's updated in the app
        System.out.println();
        System.out.println("Returning to Home Screen...");
        displayHomeScreen();

    }





    static void displayPaymentScreen()
    {
        System.out.println();
        System.out.println("Please Enter Your Payment (Debit) Information Below");
        System.out.println("------------------------------------------------------");

        // user inputs
        System.out.print("Please enter the amount you spent: ");
        double amountSpent;

        // try catch so the app doesn't crash if user inputs a string or nothing when asked for $ amount
        try
        {
            amountSpent = Double.parseDouble(userInput.nextLine());

            // makes it so user can't put a negative number inside something that should only be positive numbers
            if (amountSpent < 0)
            {
                System.out.println();
                System.out.println("Invalid amount, please try again");
                displayPaymentScreen();
                return;
            }
        }
        catch (Exception ex)
        {
            System.out.println();
            System.out.println("Invalid number, please try again");
            displayPaymentScreen();
            return;
        }


        System.out.print("Please enter the date of the payment using MM/DD/YYYY format: ");
        String userDateInput = userInput.nextLine();

        // fixes formatting of the date, makes it American standard
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate dateOfTransaction;

        // try catch so the app doesn't crash if user inputs a date in the wrong format
        try
        {
            dateOfTransaction = LocalDate.parse(userDateInput, formatter);
        }
        catch (Exception ex)
        {
            System.out.println();
            System.out.println("Invalid date format, please try again");
            displayPaymentScreen();
            return;
        }


        System.out.print("Please enter the name or description of the product: ");
        String productDescription = userInput.nextLine().strip();


        System.out.print("Please enter the name of the vendor: ");
        String vendorName = userInput.nextLine().strip();


        // add timestamp for hours,mins, and secs without user input
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeStamp = now.format(formatter2);


        // calls logTransactions method
        // plugs in user input variables above
        // the user inputs are recorded onto the .csv using logTransactions initial variables
        logTransactions(dateOfTransaction.format(formatter), timeStamp, productDescription, vendorName, -amountSpent); // records user $ input as negative


        System.out.println();
        System.out.println("Payment Recorded");
        transactions = loadTransactions(); // reloads the arraylist/.csv so it's updated in the app
        System.out.println();
        System.out.println("Returning to Home Screen...");
        displayHomeScreen();

    }





    static void displayLedgerScreen()
    {
        System.out.println();
        System.out.println("Ledgers Display");
        System.out.println("---------------------------------");
        System.out.println("A) Display All Entries");
        System.out.println("D) Deposits");
        System.out.println("P) Payments");
        System.out.println("R) View Reports");
        System.out.println("H) Back To Home Screen");
        System.out.println();
        System.out.print("Make your selection here: ");
        String selection2 = userInput.nextLine().toUpperCase().strip();

        System.out.println();

        switch (selection2)
        {
            case "A":
                displayAllTransactions();
                displayLedgerScreen();
                break;

            case "D":
                displayDepositsOnly();
                displayLedgerScreen();
                break;

            case "P":
                displayPaymentsOnly();
                displayLedgerScreen();
                break;

            case "R":
                displayReportsScreen();
                break;

            case "H":
                displayHomeScreen();
                break;

            // easter egg 2
            case "Z":
                String art = """
                    ⢰⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶⣶
                    ⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣇⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣿⣿⣿⢹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                    ⢸⣿⠇⣿⣿⣿⣿⣿⣿⣿⢣⣿⣿⣿⣿⡟⣿⣿⣿⣿⣿⡜⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣝⢿⢻⣿⣿⡌⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                    ⢸⣿⢸⣿⣿⣿⣿⣿⣿⡟⣼⣿⣿⣿⣿⡇⣿⣿⣿⣿⣿⣷⢹⣿⡟⣿⣿⣿⣿⣿⣿⣿⣿⣇⡜⣿⢸⣿⣿⣇⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                    ⢸⣟⣿⣿⣿⣿⣿⣿⣿⢇⣿⣿⡿⣿⣿⡇⣿⣿⣿⣿⣿⣿⡎⣿⡇⣿⣿⣿⣿⣿⣿⡟⡟⣸⣿⡜⢸⣿⣿⣿⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿
                    ⢸⣿⣿⣿⣿⣿⣿⣿⣿⢸⣿⡿⡄⣿⣿⡇⣿⣿⣿⣿⣿⣿⣿⡜⣷⢹⣿⣿⣿⣿⣿⠱⣱⣿⣿⣿⢸⣿⣿⣿⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿
                    ⢸⣿⣿⣿⣿⣿⣿⣿⡏⣾⣿⢱⣷⢸⣿⡇⡘⣿⣿⣿⣿⣿⣿⣷⡸⡏⣿⣿⣿⣿⠇⣠⣿⣿⣿⣿⢸⠛⣿⣿⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿
                    ⢸⣿⣿⣿⣿⣿⣿⣿⡇⣿⢃⠻⢿⡇⣿⡇⣷⠹⣿⣿⣿⣿⣿⣿⣷⡹⡘⣿⣿⡟⣄⣿⣿⣿⣿⣿⢸⢰⡹⡿⠇⣹⣿⣿⣿⣿⣿⣿⣿⣿
                    ⢸⣿⣿⣿⣿⣿⣿⣿⢸⡏⣾⣿⣶⣬⠘⠃⢿⣧⠹⣿⣿⣿⣿⣿⣿⣷⡑⡽⢏⣼⢸⣿⣿⣿⣿⡿⠈⣈⣥⢲⡇⡹⣿⣿⣿⣿⣿⣿⣿⣿
                    ⢸⣿⣿⣿⣿⣿⣿⡟⢸⣸⣿⣿⣿⣿⣇⠇⢦⣈⠑⠹⢿⣿⣿⣿⣿⣿⣿⣆⠹⡇⣿⣿⠿⠛⢉⡴⣰⣿⣿⣇⠇⣧⢹⣿⣿⣿⣿⣿⣿⣿
                    ⢸⣿⣿⣿⣿⣿⣿⠇⣆⣿⣿⣿⣿⣿⣿⡄⢸⠿⣷⣶⡄⢬⣽⣿⣿⣿⣿⣿⡗⣘⠋⣤⠴⣞⣛⣿⣿⣿⣿⢿⡀⣿⡇⣿⣿⣿⣿⣿⣿⣿
                    ⢸⣿⣿⣿⣿⣿⣿⡇⣸⣟⠛⠿⠿⢛⣛⠿⠼⣿⣶⣿⣿⣮⠻⣿⣿⣿⣿⡿⡑⣿⣷⣬⣿⠿⠿⣛⠫⠭⠓⠚⣉⣥⣼⢸⣿⣿⣿⣿⢿⣿
                    ⢸⣿⣿⣿⣿⣿⣿⢇⢹⣿⣿⣷⡆⢠⣤⣭⣀⣐⡊⠉⠿⢿⣷⣝⢿⣿⡿⣱⣷⡸⣿⠛⢓⣈⣩⣤⣶⣶⣿⠀⣿⣿⣿⢸⣿⣿⡻⣿⢸⣿
                    ⢸⣿⣿⣿⣿⣿⣿⣞⡌⣿⣿⣿⣧⡈⢿⣿⣿⣿⣿⠟⣰⣾⣿⣿⢸⠟⢴⣿⣿⣧⠹⣿⣌⠻⠿⣿⠿⠟⢁⣾⣿⣿⣿⢸⣿⣿⣷⣝⢸⣿
                    ⢸⣿⣿⣿⣿⣿⡟⢿⡱⢹⣿⣿⣿⣿⣦⣬⣍⣭⣤⣾⣿⣿⣿⠣⢣⣾⣷⣌⠻⣿⣧⢻⣿⣷⣶⣶⣶⣾⣿⣿⣿⣿⡇⡟⣿⣿⣿⣿⢘⣿
                    ⢸⣿⣿⣿⡏⣿⡇⣮⡛⣄⡻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⢃⣴⣿⣿⣿⣿⣿⣦⣙⠧⡹⣿⣿⣿⣿⣿⣿⣿⣿⡟⠘⢤⣿⣿⣿⠇⣿⢨
                    ⢸⡿⣿⣿⣷⢹⣇⢿⣿⣦⠝⠪⡻⣿⣿⣿⣿⣿⣿⣿⣿⣷⣿⣿⣿⣿⣛⣿⣿⣿⣿⣷⣦⣹⣿⣿⣿⣿⣿⣿⣿⠁⡼⣸⣿⣿⡟⣼⡏⣾
                    ⢸⣿⣿⣿⣿⡎⣿⠸⣿⣿⣷⡈⢶⣦⣽⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢣⣾⢣⣿⣿⠟⣼⡿⣰⣿
                    ⢸⡟⣿⣿⣿⣷⠸⣇⢻⣿⣿⣿⣌⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⣡⣿⢣⣾⡿⢋⣾⡿⢡⣿⣿
                    ⢸⡇⣿⣿⣿⣿⡇⢻⡌⣿⣿⣿⣿⣷⣝⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⢋⣤⡙⣿⣿⣿⣿⣿⣿⣿⣿⠟⣩⣾⣿⢣⣾⠟⢥⡿⡋⢠⣿⣿⣿
                    ⢸⡇⢹⣿⣿⣿⡇⣆⠳⡜⢿⣿⣿⣿⣿⣿⣮⣙⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠟⢋⣵⠿⠿⣋⡵⢛⠁⢴⣫⡾⢡⣿⣿⣿⣿
                    ⢸⡇⡄⢿⣿⣿⣷⢹⣦⡙⣮⡻⣿⣿⣿⣿⣿⣿⣷⣦⡝⡻⢿⣿⣿⣿⣿⣿⣿⡿⠟⠋⡁⣾⣶⣶⣦⣶⣶⣶⠏⣼⣿⠟⡄⡎⣿⣿⣿⣿
                    ⢸⣿⣿⡨⡻⣿⣿⣦⠹⣿⣮⣑⠮⣙⠛⠛⢋⣥⣾⣿⢇⣿⣦⠨⣽⣛⣛⣛⣭⣶⢣⣾⡇⠿⣿⣿⣿⣿⡿⢋⣾⡿⢋⡼⢰⢡⣿⣿⣿⣿
                    ⢸⣿⣿⣧⢻⣮⡻⢿⣷⣌⡻⢿⣿⣶⣶⣿⣿⣿⣿⡟⢨⣝⢿⣧⣝⢿⣿⡿⢛⣵⣾⡿⣣⠀⠈⠙⠻⢟⣵⠿⣋⣴⡿⢡⢃⣿⣿⣿⣿⣿
                    ⢸⣿⣿⣿⣆⢿⣿⡄⣭⣛⠿⠶⣬⣝⣛⠿⣿⡿⠋⠀⣼⣿⣮⡛⣿⣆⠻⣱⣿⢟⣵⣾⣿⡆⠀⡠⠔⠋⠁⠀⠉⠉⡱⠃⠛⠋⠉⠉⠉⠉
                    ⠸⠯⠿⠿⠿⠦⠻⠷⠜⠿⠿⠷⠶⠖⠛⠋⠈⠀⠀⠰⠿⠿⠿⠿⠜⠿⠤⠿⠇⠾⠿⠿⠿⠇⠀⠀⠀⠀⠀⠀⠀⠞⠀⠀⠀⠀⠀⠀⠀⠀
                    """;
                System.out.println(art);
                displayLedgerScreen();
                break;

            default:
                System.out.println();
                System.out.println("Error, please try again");
                displayLedgerScreen();
        }

    }





    // method used inside displayLedgerScreen();
    static void displayAllTransactions()
    {
        // allTransactions is a temporary variable created to make this loop work
        for (TransactionsInfo allTransactions : transactions)
        {
            System.out.println(allTransactions.getDate() + "|" + allTransactions.getTime() + "|" + allTransactions.getDescription() + "|"
                    + allTransactions.getVendor() + "|$" + allTransactions.getAmount());
        }
    }





    // method used inside displayLedgerScreen();
    static void displayDepositsOnly()
    {
        // depositTransactions is a temporary variable created to make this loop work
        for (TransactionsInfo depositTransactions : transactions)
        {
            // if loop that makes it so only deposits are printed, since they can't be negative
            if (depositTransactions.getAmount() >= 0)
            {
            System.out.println(depositTransactions.getDate() + "|" + depositTransactions.getTime() + "|" + depositTransactions.getDescription()
                    + "|" + depositTransactions.getVendor() + "|$" + depositTransactions.getAmount());
            }
        }
    }





    // method used inside displayLedgerScreen();
    static void displayPaymentsOnly()
    {
        // paymentTransactions is a temporary variable created to make this loop work
        for (TransactionsInfo paymentTransactions : transactions)
        {
            // if loop that makes it so only transactions that are negative/subtracting are printed
            if (paymentTransactions.getAmount() <= 0)
            {
                System.out.println(paymentTransactions.getDate() + "|" + paymentTransactions.getTime() + "|"
                        + paymentTransactions.getDescription() + "|" + paymentTransactions.getVendor() + "|$" + paymentTransactions.getAmount());
            }
        }
    }





    static void displayReportsScreen()
    {
        System.out.println();
        System.out.println("Reports Search");
        System.out.println("---------------------------------");
        System.out.println("1) Month To Date");
        System.out.println("2) Previous Month");
        System.out.println("3) Year To Date");
        System.out.println("4) Previous Year");
        System.out.println("5) Search By Vendor");
        System.out.println("6) Custom Search");
        System.out.println("0) Back To Ledger Screen");
        System.out.println();
        System.out.print("Make your selection here: ");
        String selection3 = userInput.nextLine().strip();

        System.out.println();

        switch (selection3)
        {
            case "1":
                monthToDateFilter();
                displayReportsScreen();
                break;

            case "2":
                previousMonthFilter();
                displayReportsScreen();
                break;

            case "3":
                yearToDateFilter();
                displayReportsScreen();
                break;

            case "4":
                previousYearFilter();
                displayReportsScreen();
                break;

            case "5":
                System.out.println();
                System.out.print("Enter the vendor name you would like to search for: ");
                String vendorInput = userInput.nextLine().strip();
                vendorSearch(vendorInput);
                displayReportsScreen();
                break;

            case "6":
                displayCustomSearch();
                break;

            case "0":
                displayLedgerScreen();
                break;

            // easter egg 4
            case "7":
                String art = """
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣷⣶⣤⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠸⣿⣿⣿⣿⣷⡒⢄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣿⣿⣿⣿⣿⣆⠙⡄⠀⠐⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣤⣤⣤⣤⣤⣤⣤⣤⣤⠤⢄⡀⠀⠀⣿⣿⣿⣿⣿⣿⡆⠘⡄⠀⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⢿⣿⣿⣿⣿⣿⣿⣿⣦⡈⠒⢄⢸⣿⣿⣿⣿⣿⣿⡀⠱⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠻⣿⣿⣿⣿⣿⣿⣿⣦⠀⠱⣿⣿⣿⣿⣿⣿⣇⠀⢃⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⢿⣿⣿⣿⣿⣿⣿⣷⡄⣹⣿⣿⣿⣿⣿⣿⣶⣾⣿⣶⣤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣴⣶⣿⣭⣍⡉⠙⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⢀⣠⣶⣿⣿⣿⣿⣿⣿⣿⣿⣷⣦⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡇⠀⠀⠀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠉⠉⠛⠻⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡷⢂⣓⣶⣶⣶⣶⣤⣤⣄⣀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⢿⣿⣿⣿⠟⢀⣴⢿⣿⣿⣿⠟⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⠿⠛⠋⠉⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠤⠤⠤⠤⠙⣻⣿⣿⣿⣿⣿⣿⣾⣿⣿⡏⣠⠟⡉⣾⣿⣿⠋⡠⠊⣿⡟⣹⣿⢿⣿⣿⣿⠿⠛⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣤⣶⣤⣭⣤⣼⣿⢛⣿⣿⣿⣿⣻⣿⣿⠇⠐⢀⣿⣿⡷⠋⠀⢠⣿⣺⣿⣿⢺⣿⣋⣉⣉⣩⣴⣶⣤⣤⣄⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠛⠻⠿⣿⣿⣿⣇⢻⣿⣿⡿⠿⣿⣯⡀⠀⢸⣿⠋⢀⣠⣶⠿⠿⢿⡿⠈⣾⣿⣿⣿⣿⡿⠿⠛⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠻⢧⡸⣿⣿⣿⠀⠃⠻⠟⢦⢾⢣⠶⠿⠏⠀⠰⠀⣼⡇⣸⣿⣿⠟⠉⠀⠀⢀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣴⣾⣶⣽⣿⡟⠓⠒⠀⠀⡀⠀⠠⠤⠬⠉⠁⣰⣥⣾⣿⣿⣶⣶⣷⡶⠄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠉⠉⠹⠟⣿⣿⡄⠀⠀⠠⡇⠀⠀⠀⠀⠀⢠⡟⠛⠛⠋⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⠋⠹⣷⣄⠀⠐⣊⣀⠀⠀⢀⡴⠁⠣⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⣤⣀⠤⠊⢁⡸⠀⣆⠹⣿⣧⣀⠀⠀⡠⠖⡑⠁⠀⠀⠀⠑⢄⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⣦⣶⣿⣿⣟⣁⣤⣾⠟⠁⢀⣿⣆⠹⡆⠻⣿⠉⢀⠜⡰⠀⠀⠈⠑⢦⡀⠈⢾⠑⡾⠲⣄⠀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⠀⠀⠀⣀⣤⣶⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠖⠒⠚⠛⠛⠢⠽⢄⣘⣤⡎⠠⠿⠂⠀⠠⠴⠶⢉⡭⠃⢸⠃⠀⣿⣿⣿⠡⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                ⠀⠀⠀⠀⠀⡤⠶⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣋⠁⠀⠀⠀⠀⠀⢹⡇⠀⠀⠀⠀⠒⠢⣤⠔⠁⠀⢀⡏⠀⠀⢸⣿⣿⠀⢻⡟⠑⠢⢄⡀⠀⠀⠀⠀
                ⠀⠀⠀⠀⢸⠀⠀⠀⡀⠉⠛⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣷⣄⣀⣀⡀⠀⢸⣷⡀⣀⣀⡠⠔⠊⠀⠀⢀⣠⡞⠀⠀⠀⢸⣿⡿⠀⠘⠀⠀⠀⠀⠈⠑⢤⠀⠀
                ⠀⠀⢀⣴⣿⡀⠀⠀⡇⠀⠀⠀⠈⣿⣿⣿⣿⣿⣿⣿⣿⣝⡛⠿⢿⣷⣦⣄⡀⠈⠉⠉⠁⠀⠀⠀⢀⣠⣴⣾⣿⡿⠁⠀⠀⠀⢸⡿⠁⠀⠀⠀⠀⠀⠀⠀⠀⡜⠀⠀
                ⠀⢀⣾⣿⣿⡇⠀⢰⣷⠀⢀⠀⠀⢹⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣶⣦⣭⣍⣉⣉⠀⢀⣀⣤⣶⣾⣿⣿⣿⢿⠿⠁⠀⠀⠀⠀⠘⠀⠀⠀⠀⠀⠀⠀⠀⠀⡰⠉⢦⠀
                ⢀⣼⣿⣿⡿⢱⠀⢸⣿⡀⢸⣧⡀⠀⢿⣿⣿⠿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡭⠖⠁⠀⡠⠂⠀⠀⠀⠀⠀⠀⠀⠀⢠⠀⠀⠀⢠⠃⠀⠈⣀
                ⢸⣿⣿⣿⡇⠀⢧⢸⣿⣇⢸⣿⣷⡀⠈⣿⣿⣇⠈⠛⢿⣿⣿⣿⣿⣿⣿⠿⠿⠿⠿⠿⠿⠟⡻⠟⠉⠀⠀⡠⠊⠀⢠⠀⠀⠀⠀⠀⠀⠀⠀⣾⡄⠀⢠⣿⠔⠁⠀⢸
                ⠈⣿⣿⣿⣷⡀⠀⢻⣿⣿⡜⣿⣿⣷⡀⠈⢿⣿⡄⠀⠀⠈⠛⠿⣿⣿⣿⣷⣶⣶⣶⡶⠖⠉⠀⣀⣤⡶⠋⠀⣠⣶⡏⠀⠀⠀⠀⠀⠀⠀⢰⣿⣧⣶⣿⣿⠖⡠⠖⠁
                ⠀⣿⣿⣷⣌⡛⠶⣼⣿⣿⣷⣿⣿⣿⣿⡄⠈⢻⣷⠀⣄⡀⠀⠀⠀⠈⠉⠛⠛⠛⠁⣀⣤⣶⣾⠟⠋⠀⣠⣾⣿⡟⠀⠀⠀⠀⠀⠀⠀⠀⣿⣿⣿⣿⣿⠷⠊⠀⢰⠀
                ⢰⣿⣿⠀⠈⢉⡶⢿⣿⣿⣿⣿⣿⣿⣿⣿⣆⠀⠙⢇⠈⢿⣶⣦⣤⣀⣀⣠⣤⣶⣿⣿⡿⠛⠁⢀⣤⣾⣿⣿⡿⠁⠀⠀⠀⠀⠀⠀⠀⣸⣿⡿⠿⠋⠙⠒⠄⠀⠉⡄
                ⣿⣿⡏⠀⠀⠁⠀⠀⠀⠉⠉⠙⢻⣿⣿⣿⣿⣷⡀⠀⠀⠀⠻⣿⣿⣿⣿⣿⠿⠿⠛⠁⠀⣀⣴⣿⣿⣿⣿⠟⠀⠀⠀⠀⠀⠀⠀⠀⢠⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠰
                """;
                System.out.println(art);
                displayReportsScreen();
                break;

            default:
                System.out.println();
                System.out.println("Error, please try again");
                displayReportsScreen();
        }

    }





    // new screen method that's used inside displayReportsScreen();
    static void displayCustomSearch()
    {
        System.out.println();
        System.out.println("CUSTOM SEARCH");
        System.out.println("-------------------------------------------");
        System.out.println("Search from one of the following:");
        System.out.println("1) Start Date");
        System.out.println("2) End Date");
        System.out.println("3) Description");
        System.out.println("4) Vendor");
        System.out.println("5) Amount of $ (search a range)");
        System.out.println("0) Exit Custom Search");
        System.out.println();
        System.out.print("Enter your selection here: ");
        String searchSelection = userInput.nextLine().strip();


        switch (searchSelection)
        {
            case "1":
                System.out.println();
                System.out.print("Enter the start date you would like to search for (MM/DD/YYYY): ");
                String startDateSearch = userInput.nextLine().strip();
                System.out.println();

                // reused code from deposit and payment screen methods
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate startDate;

                try
                {
                    startDate = LocalDate.parse(startDateSearch, formatter);
                }
                catch (Exception ex)
                {
                    System.out.println();
                    System.out.println("Invalid date format, please try again");
                    System.out.println();
                    System.out.println("Returning to Custom Search menu...");
                    displayCustomSearch();
                    return;
                }

                // same logic as the filters I made earlier
                for (TransactionsInfo entry : transactions)
                {
                    // .isAfter and .isEqual are part of LocalDate
                    if (entry.getLocalDate().isAfter(startDate) || entry.getLocalDate().isEqual(startDate))
                    {
                        System.out.println(entry.getDate() + "|" + entry.getTime() + "|" + entry.getDescription() + "|" + entry.getVendor() + "|$" + entry.getAmount());
                    }
                }

                System.out.println();
                System.out.println("Search Successful");
                System.out.println();
                System.out.println("Returning to Custom Search menu...");
                displayCustomSearch();
                break;


            case "2":
                System.out.println();
                System.out.print("Enter the end date you would like to search for (MM/DD/YYYY): ");
                String endDateSearch = userInput.nextLine().strip();
                System.out.println();

                // reused code from deposit and payment screen methods
                DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                LocalDate endDate;

                try
                {
                    endDate = LocalDate.parse(endDateSearch, formatter2);
                }
                catch (Exception ex)
                {
                    System.out.println();
                    System.out.println("Invalid date format, please try again");
                    System.out.println();
                    System.out.println("Returning to Custom Search menu...");
                    displayCustomSearch();
                    return;
                }

                // same logic as the filters I made earlier
                for (TransactionsInfo entry : transactions)
                {
                    // .isBefore and .isEqual are part of LocalDate, localdate is making me ANGRY
                    if (entry.getLocalDate().isBefore(endDate) || entry.getLocalDate().isEqual(endDate))
                    {
                        System.out.println(entry.getDate() + "|" + entry.getTime() + "|" + entry.getDescription() + "|" + entry.getVendor() + "|$" + entry.getAmount());
                    }
                }
                System.out.println();
                System.out.println("Search Successful");
                System.out.println();
                System.out.println("Returning to Custom Search menu...");
                displayCustomSearch();
                break;


            case "3":
                System.out.println();
                System.out.print("Enter the description you would like to search for: ");
                String descriptionSearch = userInput.nextLine();
                System.out.println();

                // same logic as the vendorSearch method I made
                for (TransactionsInfo transaction : transactions)
                {
                    if (transaction.getDescription().toLowerCase().contains(descriptionSearch.toLowerCase()))
                    {
                        System.out.println(transaction.getDate() + "|" + transaction.getTime() + "|" + transaction.getDescription()
                                + "|" + transaction.getVendor() + "|$" + transaction.getAmount());
                    }
                }
                System.out.println();
                System.out.println("Search Successful");
                System.out.println();
                System.out.println("Returning to Custom Search menu...");
                displayCustomSearch();
                break;


            case "4":
                System.out.println();
                System.out.print("Enter the vendor name to you would like to search for: ");
                String vendorInput2 = userInput.nextLine();
                vendorSearch(vendorInput2);
                displayCustomSearch();
                break;


            case "5":
                System.out.println();
                System.out.print("Enter a minimum amount cutoff in $: ");
                double minAmountSearch;
                double maxAmountSearch;

                // try catch so the app doesn't crash if user inputs a string or nothing when asked for $ amount
                try
                {
                    minAmountSearch = Double.parseDouble(userInput.nextLine());
                }
                catch (Exception ex)
                {
                    System.out.println();
                    System.out.println("Invalid number, please try again");
                    System.out.println();
                    System.out.println("Returning to Custom Search menu...");
                    displayCustomSearch();
                    return;
                }

                System.out.println();
                System.out.print("Enter a maximum amount cutoff in $: ");

                try
                {
                    maxAmountSearch = Double.parseDouble(userInput.nextLine());
                }

                catch (Exception ex)
                {
                    System.out.println();
                    System.out.println("Invalid number, please try again");
                    System.out.println();
                    System.out.println("Returning to Custom Search menu...");
                    displayCustomSearch();
                    return;
                }

                System.out.println();

                // same logic as the vendorSearch method I made
                for (TransactionsInfo transaction : transactions)
                {
                    // searches for a range the user inputs
                    if (transaction.getAmount() >= minAmountSearch && transaction.getAmount() <= maxAmountSearch)
                    {
                        System.out.println(transaction.getDate() + "|" + transaction.getTime() + "|" + transaction.getDescription()
                                + "|" + transaction.getVendor() + "|$" + transaction.getAmount());
                    }
                }
                System.out.println();
                System.out.println("Search Successful");
                System.out.println();
                System.out.println("Returning to Custom Search menu...");
                displayCustomSearch();
                break;


            case "0":
                displayReportsScreen();
                break;


            // easter egg 3
            case "6":
                String art = """
                        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⡒⢄⡀⠀⠀⠀⠀⡴⣏⡝⣳⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
                        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠁⠒⠿⠀⠀⣠⡾⠛⠉⠉⠉⠛⠷⣄⠀⠀⠀⣤⠖⢻⠀⠀⠀⠀
                        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⡾⠋⠀⠀⠀⠀⠀⠀⠀⠀⠙⠢⣀⠈⠓⠊⠀⠀⠀⠀
                        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣤⡴⠾⢝⡷⠁⠀⠀⢠⡤⣤⢄⣀⣀⣀⠀⠀⠈⠳⡦⣀⠀⠀⠀⠀
                        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⡤⠔⠒⠊⠉⠉⠉⠉⠉⠉⠉⠙⠓⠲⠤⢤⣈⣷⡱⢎⡳⣍⢎⡟⣆⠀⠀⠈⢯⡛⢶⣤⣀
                        ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡤⠒⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠙⠓⠷⢼⣚⣜⣺⣀⣠⡠⠤⠿⠖⠚⠚
                        ⠀⠀⠀⠀⠀⠀⠀⣤⢶⡶⣶⣶⠋⢀⣤⠶⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣩⠟⠉⠀⠀⠀⠀⠀⠀⠀
                        ⠀⠀⠀⠀⠀⠀⣾⣛⢧⢞⣽⢃⠔⠋⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣞⠁⢀⣀⣀⡀⠀⠀⠀⠀⠀
                        ⠀⠀⠀⠀⠀⢠⢿⣜⣭⣾⣗⡋⣀⣤⣴⣶⢶⢲⣛⠿⣹⢏⣟⡻⣖⢶⣲⢤⣄⣀⠀⠀⢢⡀⢲⠒⠉⠉⢿⣿⣿⢽⢺⣱⢻⣦⠀⠀⠀⠀
                        ⠀⠀⠀⠀⢠⠞⠉⣀⣀⡟⣷⠉⠉⣿⠇⠆⣬⡷⣭⣋⠅⢎⠤⢩⢉⣷⠙⣮⢓⠻⣿⣶⣄⠽⢶⣷⣲⡷⠶⣝⣮⢏⡳⣎⢷⢻⡆⠀⠀⠀
                        ⠀⠀⠀⠀⢙⡿⣟⣽⠀⣿⢼⣦⣾⣛⣬⡱⣼⡟⣦⣹⣌⢢⣑⢃⠆⣾⣩⢟⣇⣊⣽⣧⠀⠀⣼⣿⢻⢃⣀⣀⠹⣿⣱⢫⣞⢯⣿⠀⠀⠀
                        ⠀⠀⠀⠀⢸⡷⣟⣾⠀⢸⢞⣿⣿⡜⡶⣹⢮⣿⢶⣹⣏⢧⢏⣏⢻⣧⢏⣿⢲⡳⣼⢻⣁⣼⣿⠯⣟⡧⠀⢻⣽⣿⣽⣗⢮⠞⣿⠀⠀⠀
                        ⠀⠀⠀⠀⢸⣗⡯⢾⡄⢻⣯⢾⣏⢾⡱⣣⡟⢺⣳⠟⣿⣾⣚⡬⣏⣿⠾⣿⣷⢽⠲⣏⣿⠿⣍⣾⣿⡇⠀⣸⣿⡞⢶⡹⣎⡟⣼⡄⠀⠀
                        ⠀⠀⠀⠀⣼⣣⢟⡞⣧⣸⣿⣾⣏⣶⣽⠟⠁⠀⢻⡟⣼⡿⣧⡳⣭⢞⣿⡵⣺⣯⣻⡜⣽⣿⣸⣿⣿⡇⢀⣿⣿⣝⡣⣗⣣⡝⡞⡇⠀⠀
                        ⠀⠀⠀⣰⠿⣬⠳⣎⣳⣽⢻⣻⣿⣿⣁⣀⢀⠀⠀⠉⢉⠉⠈⠉⠓⠛⣃⣉⣉⠉⠉⠓⣷⣾⠿⣿⣿⣥⣿⣼⣿⣎⡗⢮⡱⣝⡺⡇⠀⠀
                        ⠀⢀⡴⣏⢟⡞⡧⢧⣟⢭⣓⢮⣽⣿⡜⡏⠙⠛⢻⡟⠛⠀⠀⠀⠀⠀⠛⠛⠻⣶⠶⠶⣾⣏⠷⣣⣿⣿⣿⡿⣿⡞⣼⢣⡛⢶⣙⣧⠀⠀
                        ⠒⠿⠽⠚⠉⡿⢾⣝⣌⣧⣾⢞⣼⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⢭⣛⣵⡇⠈⢻⠀⣿⡟⢦⢣⡝⢦⢣⢻⠀⠀
                        ⠀⠀⠀⠀⠀⡧⢇⡎⢾⣿⣿⢎⣿⣟⡳⠀⠙⠒⠒⠁⠀⠀⠀⠀⠀⠀⠀⠢⠤⠠⠤⣾⢫⢖⡱⣾⠁⠀⣸⠀⣿⣋⠎⡵⢈⠇⡅⣻⠀⠀
                        ⠀⠀⠀⠀⢸⡇⡖⢈⠆⢿⣿⡜⣼⣿⣷⣤⣀⠀⠀⠀⠀⠠⠴⠒⣤⠀⠀⠀⠀⠠⠾⣾⡡⢎⣱⣏⡀⠔⠃⠀⣿⠜⡒⠄⠣⠌⠄⡹⡄⠀
                        ⠀⠀⠀⠀⣼⢠⡁⢂⠊⠼⣿⢷⠸⣿⣟⣿⣯⡙⠒⠤⠤⢀⣀⣀⡀⣀⡠⠤⠤⠒⢪⡟⣐⣳⣿⡁⠀⠀⠀⣼⠛⣰⢊⠈⡂⠐⡀⢧⡬⠶
                        ⠀⠀⠀⢠⡇⡼⢀⠂⠀⠆⣿⢼⡅⣿⣯⣿⣿⣷⣤⡀⠀⣠⣺⣿⣿⣞⠟⣦⡀⠀⣾⣷⡼⣷⣿⠅⠀⢀⡞⢁⡶⠋⡀⠔⠠⢱⡌⢸⡄⠀
                        ⠀⠀⠀⣼⢠⡇⡀⠀⢁⠂⡟⡇⣿⣿⣳⠧⢹⠀⠀⢀⣾⡟⢿⣟⣿⠟⠖⣿⢱⣠⡇⢻⣿⣽⣿⠀⠺⠷⠒⢫⡇⠂⡁⠀⡁⣸⡇⠈⣇⠀
                        ⠀⠀⢠⡇⣸⡇⠄⠡⠀⢸⠇⢧⠸⡽⠋⠀⣻⠀⢠⣞⡿⠀⡎⢀⡇⠀⠐⣿⣏⣿⡅⢈⢿⡿⠃⠀⠀⠀⠀⢸⡀⢁⠠⠁⠄⣽⣷⠀⢻⠀
                        ⠀⠀⢸⠁⡏⡇⠠⠁⠂⣼⠀⣸⠞⠀⡐⢁⡏⢀⣿⣟⠇⢰⡡⠝⠃⠀⠀⣟⣾⡞⣇⠂⠌⢧⠀⠀⠀⠀⠀⣼⠀⠆⡀⠘⣰⣿⢽⠀⢸⡄
                        ⠀⠀⠸⠦⠇⠧⠤⠤⠥⠇⠰⠥⠤⠤⠤⠼⢁⣮⣿⣿⣀⣀⣀⣀⣀⣀⣠⣿⣷⣯⡿⠦⠤⠬⠷⠄⠀⠀⡰⠧⠴⠤⠤⠵⠿⠝⠬⠬⠼⠁
                        """;
                System.out.println(art);
                displayCustomSearch();
                break;


            default:
                System.out.println();
                System.out.println("Error, please try again");
                displayCustomSearch();
        }
    }





    // method used inside displayReportsScreen();
    static void monthToDateFilter()
    {
        // gets the current date using a variable
        LocalDate currentDate = LocalDate.now();

        // entry is a temp variable to help the loop work/filter
        for (TransactionsInfo entry : transactions)
        {
            // .getLocalDate is a helper method I have inside the TransactionsInfo class
            // .getMonth and .getYear are getters built into LocalDate import
            if (entry.getLocalDate().getMonth() == currentDate.getMonth() && entry.getLocalDate().getYear() == currentDate.getYear())
            {
                System.out.println(entry.getDate() + "|" + entry.getTime() + "|" + entry.getDescription() + "|" + entry.getVendor() + "|$" + entry.getAmount());
            }
        }
    }





    // method used inside displayReportsScreen();
    static void previousMonthFilter()
    {
        // gets the current date
        LocalDate currentDate = LocalDate.now();
        // subtracts 1 month from currentDate's month to get the previous month using LocalDate's .minusMonths
        LocalDate lastMonth = currentDate.minusMonths(1);

        // entry is a temp variable to help the loop work/filter
        for (TransactionsInfo entry : transactions)
        {
            // .getLocalDate is a helper method I have inside the TransactionsInfo class
            // .getMonth and .getYear are getters built into LocalDate import
            if (entry.getLocalDate().getMonth() == lastMonth.getMonth() && entry.getLocalDate().getYear() == lastMonth.getYear())
            {
                System.out.println(entry.getDate() + "|" + entry.getTime() + "|" + entry.getDescription() + "|" + entry.getVendor() + "|$" + entry.getAmount());
            }
        }
    }





    // method used inside displayReportsScreen();
    static void yearToDateFilter()
    {
        // gets the current date
        LocalDate currentDate = LocalDate.now();

        // entry is a temp variable to help the loop work/filter
        for (TransactionsInfo entry : transactions)
        {
            // .getLocalDate is a helper method I have inside the TransactionsInfo class
            // .getMonth and .getYear are getters built into LocalDate import
            if (entry.getLocalDate().getYear() == currentDate.getYear())
            {
                System.out.println(entry.getDate() + "|" + entry.getTime() + "|" + entry.getDescription() + "|" + entry.getVendor() + "|$" + entry.getAmount());
            }
        }
    }





    // method used inside displayReportsScreen();
    static void previousYearFilter()
    {
        // gets the current date
        LocalDate currentDate = LocalDate.now();
        // subtracts 1 year from currentDate's year to get the previous year using LocalDate's .minusYears
        LocalDate lastYear = currentDate.minusYears(1);

        // entry is a temp variable to help the loop work/filter
        for (TransactionsInfo entry : transactions)
        {
            // .getLocalDate is a helper method I have inside the TransactionsInfo class
            // .getYear is a getter built into LocalDate import
            // && not needed because I don't need to know months for this filter
            if (entry.getLocalDate().getYear() == lastYear.getYear())
            {
                System.out.println(entry.getDate() + "|" + entry.getTime() + "|" + entry.getDescription() + "|" + entry.getVendor() + "|$" + entry.getAmount());
            }
        }
    }





    // search method used inside displayReportsScreen();
    static void vendorSearch(String vendor)
    {
        System.out.println();
        for (TransactionsInfo transaction : transactions)
        {
            if (transaction.getVendor().toLowerCase().contains(vendor.toLowerCase()))
            {
                System.out.println(transaction.getDate() + "|" + transaction.getTime() + "|" + transaction.getDescription()
                        + "|" + transaction.getVendor() + "|$" + transaction.getAmount());
            }
        }
    }





    // arraylist used to load the .csv for use inside the app
    static ArrayList <TransactionsInfo> loadTransactions()
    {
        // create the container/arraylist
        ArrayList<TransactionsInfo> transactions = new ArrayList<>();

        try
        {
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            // skips header
            String lines = bufferedReader.readLine();

            // reads the .csv file
            while ((lines = bufferedReader.readLine()) != null)
            {

                // array to split the .csv into columns
                String[] columns = lines.split("\\|");
                String date = columns[0];
                String time = columns[1];
                String description = columns[2];
                String vendor = columns[3];
                double amount = Double.parseDouble(columns[4]);

                // created object
                TransactionsInfo transactionsInfo = new TransactionsInfo(date, time, description, vendor, amount);

                // adds transactions to the arraylist
                transactions.add(transactionsInfo);
            }

            bufferedReader.close();
        }

        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return transactions;
    }





    // log method used to add the user's inputs onto the .csv file
    private static void logTransactions(String dateInput, String time, String description, String vendor, double amount)
    {
        // calls the variables inside the try block
        // important to note, will be using in the future
        try (FileOutputStream fileOutputStream = new FileOutputStream("transactions.csv", true);
             PrintWriter printWriter = new PrintWriter(fileOutputStream))
        {
            // logs into the .csv file (insert formatter)
            printWriter.println(dateInput + "|" + time + "|" + description + "|" + vendor + "|" + amount);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }


}
