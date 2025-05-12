package com.splitwise.strategies;

import com.splitwise.models.Split;

import java.util.List;

public class ExactSplitStrategy implements SplitStrategy {
    @Override
    public boolean validate(List<Split> splits, double totalAmount) {
        double total = 0;
        for (Split split : splits) {
            total += split.getAmount();
        }
        return total == totalAmount;
    }
}
