package com.pluralsight;


import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class AccountingApp
{

    static Scanner userInput = new Scanner(System.in);

    static ArrayList<TransactionsInfo> transactions;

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
                displayDepositScreen();
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



    static void displayDepositScreen()
    {
        System.out.println();
        System.out.println("Please Enter Your Deposit Information Below");
        System.out.println("--------------------------------------------------");

        // user inputs
        System.out.print("Enter the name of the item: ");
        String itemName = userInput.nextLine().strip();
        System.out.print("Enter the name of the vendor: ");
        String vendorName = userInput.nextLine().strip();
        System.out.print("Enter the amount spent in $: ");
        double amountSpent = Double.parseDouble(userInput.nextLine());
        System.out.print("Please enter the date using MM/DD/YYYY format: ");
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
            System.out.println("Invalid date format, please try again");
            displayDepositScreen();
            return;
        }


        // add timestamp for hours,mins, and secs without user input
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("HH:mm:ss");
        String timeStamp = now.format(formatter2);

        // calls logTransactions method
        // plugs in user input variables above
        // the user inputs are recorded onto the .csv using logTransactions initial variables
        logTransactions(dateOfTransaction.format(formatter), timeStamp, itemName, vendorName, amountSpent);

        System.out.println();
        System.out.println("Deposit Recorded");
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
                break;

            case "H":
                displayHomeScreen();
                break;

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
                break;

            default:
                System.out.println("Error, please try again");
                displayReportsScreen();
        }

    }


    // WORK IN PROGRESS
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
            }

            bufferedReader.close();
        }

        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return transactions;
    }




    private static void logTransactions (String dateInput, String time, String description, String vendor, double amount)
    {

        FileOutputStream fileOutputStream = null;
        PrintWriter printWriter = null;

        try
        {
            fileOutputStream = new FileOutputStream("transactions.csv", true);
            printWriter = new PrintWriter(fileOutputStream);

            // gets the specific time for the transaction logs


            // logs into the .csv file (insert formatter)
            printWriter.println(dateInput + "|" + time + "|" + description + "|" + vendor + "|" + amount);
        }

        // catches any errors
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        finally
        {
            if (printWriter != null)
            {
                // PrintWriter doesn't throw an exception if it fails to close, so we don't need to catch it
                printWriter.close();
            }
        }

    }
}
