package com.example.myapplication;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class BackendPlayerController {

    static BackendPlayerService playerService = new BackendPlayerService();
    static BackendRoundCounterService roundCounterService = new BackendRoundCounterService();

    public void addNewPlayer(String name) {
        playerService.addNewPlayer(name);
    }

    public ArrayList<BackendPlayer> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    public void startTheGame() {
        playerService.startTheGame();
    }

    public void next() { playerService.next();}

    public BackendPlayer getCurrentPlayer() {
        return playerService.getCurrentPlayer();
    }


    // migrate this method to service layer
    public void punishPlayer(){
        int currentNrRounds = roundCounterService.getCounterValue();
        int newHealtPoints = getCurrentPlayer().getHealthPoints() - currentNrRounds;
        getCurrentPlayer().setHealthPoints(newHealtPoints);
        roundCounterService.resetBackendRoundCounterCounter();
    }

    public void clearPlayerList() {
        playerService.clearPlayerList();
    }
}
