package com.example.myapplication;

public class BackendRoundCounter {
    private static int roundCounterValue = 1;

    public int getRoundCounterValue() {
        return roundCounterValue;
    }

    public void setRoundCounterValue(int input) {
        this.roundCounterValue = input;
    }
}
