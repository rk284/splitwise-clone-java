package com.splitwise.services;

import java.util.*;

public class DebtSimplifier {

    public static void simplify(Map<String, Map<String, Double>> balanceSheet) {
        // Step 1: Calculate net balances
        Map<String, Double> netBalance = new HashMap<>();

        for (String user : balanceSheet.keySet()) {
            for (Map.Entry<String, Double> entry : balanceSheet.get(user).entrySet()) {
                //String otherUser = entry.getKey();
                double amount = entry.getValue();
                netBalance.put(user, netBalance.getOrDefault(user, 0.0) + amount);
            }
        }

        // Step 2: Separate debtors and creditors
        PriorityQueue<Map.Entry<String, Double>> debtors = new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));
        PriorityQueue<Map.Entry<String, Double>> creditors = new PriorityQueue<>((a, b) -> Double.compare(b.getValue(), a.getValue()));

        for (Map.Entry<String, Double> entry : netBalance.entrySet()) {
            double amount = entry.getValue();
            if (amount < -0.01) {
                debtors.offer(new AbstractMap.SimpleEntry<>(entry.getKey(), amount));
            } else if (amount > 0.01) {
                creditors.offer(new AbstractMap.SimpleEntry<>(entry.getKey(), amount));
            }
        }

        // Step 3: Settle debts
        while (!debtors.isEmpty() && !creditors.isEmpty()) {
            Map.Entry<String, Double> debtor = debtors.poll();
            Map.Entry<String, Double> creditor = creditors.poll();

            double debt = Math.min(-debtor.getValue(), creditor.getValue());
            System.out.println(debtor.getKey() + " pays " + debt + " to " + creditor.getKey());

            double newDebtorBalance = debtor.getValue() + debt;
            double newCreditorBalance = creditor.getValue() - debt;

            if (Math.abs(newDebtorBalance) > 0.01) {
                debtors.offer(new AbstractMap.SimpleEntry<>(debtor.getKey(), newDebtorBalance));
            }
            if (Math.abs(newCreditorBalance) > 0.01) {
                creditors.offer(new AbstractMap.SimpleEntry<>(creditor.getKey(), newCreditorBalance));
            }
        }
    }
   

}
