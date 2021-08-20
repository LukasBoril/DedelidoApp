package com.example.myapplication;

public class BackendRoundCounterService {
    private static BackendRoundCounter backendRoundCounter = new BackendRoundCounter();

    public void increaseCounter() {
        backendRoundCounter.setRoundCounterValue(backendRoundCounter.getRoundCounterValue()+1);
    }

    public void resetBackendRoundCounterCounter() {
        backendRoundCounter.setRoundCounterValue(1);
    }

    public int getCounterValue() {
        return backendRoundCounter.getRoundCounterValue();
    }
}
