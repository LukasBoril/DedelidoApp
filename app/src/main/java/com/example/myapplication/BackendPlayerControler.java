package com.example.myapplication;

import java.util.ArrayList;

public class BackendPlayerControler {

    static BackendPlayerService playerService = new BackendPlayerService();

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
}
