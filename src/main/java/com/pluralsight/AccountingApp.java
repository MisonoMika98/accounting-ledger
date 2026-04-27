package com.pluralsight;


import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class AccountingApp
{

    static Scanner userInput = new Scanner(System.in);

    static void main()
    {
        displayHomeScreen();
    }



    static void displayHomeScreen()
    {
        System.out.println();
        System.out.println("Accounting Ledger Application");
        System.out.println("---------------------------------");
        System.out.println("D) Add Deposit");
        System.out.println("P) Make Payment (Debit)");
        System.out.println("L) Ledger Display");
        System.out.println("X) Exit");
        System.out.println();
        System.out.print("Make a choice: ");
        String choice = userInput.nextLine().toUpperCase().strip();

        System.out.println();

        switch (choice)
        {
            case "D":
                System.out.println("deposit function placeholder");
                break;

            case "P":
                System.out.println("payment function placeholder");
                break;

            case "L":
                displayLedgerScreen();
                break;

            case "X":
                System.out.println("See you next time");
                break;

            default:
                System.out.println("Error, please try again");
                displayHomeScreen();
        }

    }



    static void displayLedgerScreen()
    {
        System.out.println();
        System.out.println("Ledgers Display");
        System.out.println("---------------------------------");
        System.out.println("A) Display All Entries");
        System.out.println("D) Deposits");
        System.out.println("P) Payments");
        System.out.println("R) Reports");
        System.out.println("H) Back To Home Screen");
        System.out.println();
        System.out.print("Make a choice: ");
        String choice2  = userInput.nextLine().toUpperCase().strip();

        System.out.println();

        switch (choice2)
        {
            case "A":
                System.out.println("display all entries placeholder");
                break;

            case "D":
                System.out.println("display entries that are deposits into account placeholder");
                break;

            case "P":
                System.out.println("display only negative payments/entries placeholder");
                break;

            case "R":
                System.out.println("Reports screen placeholder");
                break;

            case "H":
                displayHomeScreen();

            default:
                System.out.println("Error, please try again");
                displayLedgerScreen();

        }

    }











    // WORK IN PROGRESS
    // methods to create and log user transactions in a .csv file (W.I.P)
    private static boolean writeHeader = false;

    private static void logTransactions(String userDateInput, String action, String message, double amount) {

        FileOutputStream fileOutputStream = null;
        PrintWriter printWriter = null;

        try
        {
            fileOutputStream = new FileOutputStream("transactions.csv", true);
            printWriter = new PrintWriter(fileOutputStream);

            // gets the specific time for the transaction logs
//            LocalDateTime now = LocalDateTime.now();
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//            String timeStamp = now.format(formatter);

            // header for the .csv file, only prints ONCE thanks to the boolean,
            // but will print again if the app is restarted
            if (!writeHeader)
            {
                printWriter.printf("%-15s %-10s %-25s %-15s %-10s%n", "date", "time", "description", "vendor", "amount");
                writeHeader = true;
            }

            // logs into the .csv file (insert formatter)
            printWriter.println(userDateInput + "|" + "|" + action + "|" + message + "|" + amount);
            printWriter.printf("%-15s %-10s %-25s %-15s%n", "|", "|", "|", "|");
        }

        // catches any errors
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        finally
        {
            if(printWriter != null)
            {
                // PrintWriter doesn't throw an exception if it fails to close, so we don't need to catch it
                printWriter.close();
            }
        }

    }
}
