package com.pluralsight;


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
                System.out.println("ledger display screen placeholder");
                break;

            case "X":
                System.out.println("See you next time");
                break;

            default:
                System.out.println("Error, please try again");
                displayHomeScreen();
        }

    }
}
