package com.splitwise.strategies;

import com.splitwise.models.Split;

import java.util.List;

public interface SplitStrategy {
    boolean validate(List<Split> splits, double totalAmount);
}
