package com.example.myapplication;

import java.util.ArrayList;

public class BackendPlayerService {

    static ArrayList<BackendPlayer> playerList = new ArrayList<>();

    public void addNewPlayer(String name) {
        BackendPlayer newPlayer = new BackendPlayer(name);
        playerList.add(newPlayer);
    }

    public ArrayList<BackendPlayer> getAllPlayers() {
        return playerList;
    }

    public void startTheGame() {
        playerList.get(0).setYourTurn(true);
    }

    public void next() {
        int currentID = getCurrentPlayer().getId();
        if (currentID==playerList.size()) {
            playerList.get(currentID-1).setYourTurn(false);
            playerList.get(0).setYourTurn(true);
        }
        else {
            playerList.get(currentID-1).setYourTurn(false);
            playerList.get(currentID).setYourTurn(true);
        }
    }

    public BackendPlayer getCurrentPlayer() {
        return playerList.stream().filter(player -> player.getYourTurn()==true).findFirst().orElse(null);
    }
}
