package com.splitwise;

import com.splitwise.models.*;
import com.splitwise.services.ExpenseService;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Runtime.getRuntime().addShutdownHook(new Thread(sc::close));

        ExpenseService expenseService = new ExpenseService();

        System.out.println("Splitwise CLI started.");
        System.out.println("Add users before continuing. Type 'done' when finished.");

        // Take user input for adding users
        while (true) {
            System.out.print("Add user (format: userId name email phone) or 'done': ");
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("done")) break;

            String[] parts = line.split(" ");
            if (parts.length != 4) {
                System.out.println("Invalid input. Please use format: userId name email phone");
                continue;
            }

            String userId = parts[0];
            String name = parts[1];
            String email = parts[2];
            String phone = parts[3];

            try {
                expenseService.addUser(userId, name, email, phone);
                System.out.println("User added: " + userId);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        System.out.println("You can now add expenses or show balances.");
        System.out.println("Type 'exit' to quit.\n");

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.equalsIgnoreCase("exit")) break;

            String[] tokens = line.split(" ");
            String command = tokens[0];

            switch (command) {
                case "SHOW" -> {
                    if (tokens.length == 1) {
                        expenseService.showBalances();
                    } else {
                        expenseService.showBalance(tokens[1]);
                    }
                }

                case "EXPENSE" -> {
                    String paidBy = tokens[1];
                    double amount = Double.parseDouble(tokens[2]);
                    int n = Integer.parseInt(tokens[3]);

                    List<Split> splits = new ArrayList<>();
                    for (int i = 0; i < n; i++) {
                        String userId = tokens[4 + i];
                        splits.add(new EqualSplit(expenseService.getUser(userId)));
                    }

                    ExpenseType type = ExpenseType.valueOf(tokens[4 + n]);

                    if (type == ExpenseType.EXACT) {
                        splits.clear();
                        for (int i = 0; i < n; i++) {
                            String userId = tokens[4 + i];
                            double splitAmount = Double.parseDouble(tokens[5 + n + i]);
                            splits.add(new ExactSplit(expenseService.getUser(userId), splitAmount));
                        }
                    }

                    if (type == ExpenseType.PERCENT) {
                        splits.clear();
                        for (int i = 0; i < n; i++) {
                            String userId = tokens[4 + i];
                            double percent = Double.parseDouble(tokens[5 + n + i]);
                            splits.add(new PercentSplit(expenseService.getUser(userId), percent));
                        }
                    }

                    try {
                        expenseService.addExpense(type, amount, paidBy, splits);
                        System.out.println("Expense added.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }
                case "SIMPLIFY" -> expenseService.simplifyDebts();

                case "PRINTSHEET" -> expenseService.printBalance();


                default -> System.out.println("Invalid command");
            }
        }

        System.out.println("Splitwise CLI terminated.");
    }
}
