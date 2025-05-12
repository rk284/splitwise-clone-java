package com.splitwise.strategies;

import com.splitwise.models.Split;

import java.util.List;

public class EqualSplitStrategy implements SplitStrategy {
    @Override
    public boolean validate(List<Split> splits, double totalAmount) {
        return true; // no validation needed
    }
}
