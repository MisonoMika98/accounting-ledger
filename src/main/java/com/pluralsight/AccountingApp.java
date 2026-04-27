package com.pluralsight;


import java.io.FileOutputStream;
import java.io.PrintWriter;
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
        System.out.print("Make your selection here: ");
        String selection = userInput.nextLine().toUpperCase().strip();

        System.out.println();

        switch (selection)
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
        System.out.println("R) View Reports");
        System.out.println("H) Back To Home Screen");
        System.out.println();
        System.out.print("Make your selection here: ");
        String selection2 = userInput.nextLine().toUpperCase().strip();

        System.out.println();

        switch (selection2)
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
                displayReportsScreen();

            case "H":
                displayHomeScreen();

            default:
                System.out.println("Error, please try again");
                displayLedgerScreen();
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
        System.out.println("0) Back To Ledger Screen");
        System.out.println();
        System.out.print("Make your selection here: ");
        String selection3 = userInput.nextLine().strip();

        System.out.println();

        switch (selection3)
        {
            case "1":
                System.out.println("month to date placeholder");
                break;

            case "2":
                System.out.println("previous month placeholder");
                break;

            case "3":
                System.out.println("year to date placeholder");
                break;

            case "4":
                System.out.println("previous year placeholder");
                break;

            case "5":
                System.out.println("vendor search function placeholder");
                break;

            case "0":
                displayLedgerScreen();

            default:
                System.out.println("Error, please try again");
                displayReportsScreen();
        }

    }







    // WORK IN PROGRESS
    // methods to create and log user transactions in a .csv file (W.I.P)
    private static boolean writeHeader = false;

    private static void logTransactions (String userDateInput, String action, String message,double amount)
    {

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
        }

        // catches any errors
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        } finally
        {
            if (printWriter != null)
            {
                // PrintWriter doesn't throw an exception if it fails to close, so we don't need to catch it
                printWriter.close();
            }
        }

    }
}
