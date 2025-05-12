package com.splitwise.strategies;

import com.splitwise.models.PercentSplit;
import com.splitwise.models.Split;

import java.util.List;

public class PercentSplitStrategy implements SplitStrategy {
    @Override
    public boolean validate(List<Split> splits, double totalAmount) {
        double percentSum = 0;
        for (Split split : splits) {
            if (!(split instanceof PercentSplit)) return false;
            percentSum += ((PercentSplit) split).getPercent();
        }
        return percentSum == 100;
    }
}
