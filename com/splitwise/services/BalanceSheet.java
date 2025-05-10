
package com.splitwise.services;

import java.util.*;

public class BalanceSheet {

    private final Map<String, Map<String, Double>> balanceSheet = new HashMap<>();

    public void addUser(String userId) {
        balanceSheet.putIfAbsent(userId, new HashMap<>());
    }

    public void updateBalance(String paidBy, String paidTo, double amount) {
        balanceSheet.putIfAbsent(paidBy, new HashMap<>());
        balanceSheet.putIfAbsent(paidTo, new HashMap<>());

        Map<String, Double> balancesPaidBy = balanceSheet.get(paidBy);
        balancesPaidBy.put(paidTo, balancesPaidBy.getOrDefault(paidTo, 0.0) + amount);

        Map<String, Double> balancesPaidTo = balanceSheet.get(paidTo);
        balancesPaidTo.put(paidBy, balancesPaidTo.getOrDefault(paidBy, 0.0) - amount);
    }

    public void showBalances() {
        boolean noBalances = true;

        for (String user1 : balanceSheet.keySet()) {
            for (Map.Entry<String, Double> entry : balanceSheet.get(user1).entrySet()) {
                String user2 = entry.getKey();
                double amount = entry.getValue();
                if (amount > 0) {
                    System.out.println(user2 + " owes " + user1 + ": " + amount);
                    noBalances = false;
                }
            }
        }

        if (noBalances) {
            System.out.println("No balances");
        }
    }

    public void showBalance(String userId) {
        boolean noBalances = true;

        if (!balanceSheet.containsKey(userId)) {
            System.out.println("User " + userId + " not found.");
            return;
        }

        for (Map.Entry<String, Double> entry : balanceSheet.get(userId).entrySet()) {
            String user2 = entry.getKey();
            double amount = entry.getValue();
            if (amount > 0) {
                System.out.println(user2 + " owes " + userId + ": " + amount);
                noBalances = false;
            }
        }

        if (noBalances) {
            System.out.println("No balances for user " + userId);
        }
    }

    // FOR SIMPLIFICATION LOGIC
    public Map<String, Map<String, Double>> getBalanceSheet() {
        return this.balanceSheet;
    }
}


