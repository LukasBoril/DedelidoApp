package com.example.myapplication;

public class BackendRoundCounterController {
    private static BackendRoundCounterService backendRoundCounterService = new BackendRoundCounterService();

    public void increaseCounter() {
        backendRoundCounterService.increaseCounter();
    }

    public void resetCounter() {
        backendRoundCounterService.resetBackendRoundCounterCounter();
    }

    public int getCounterValue() {
        return backendRoundCounterService.getCounterValue();
    }

}
