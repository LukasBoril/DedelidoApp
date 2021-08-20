package com.example.myapplication;

import java.util.ArrayList;

public class BackendCardService {

    private ArrayList<Card> cardStack = new ArrayList<>();
    private String[] colors = {"green", "yellow", "blue", "red", "white"};
    private String[] animals = {"flamingo", "camel", "turtoise", "penguin", "zebra"};

    public BackendCardService() {
        for (int x=0 ; x<3 ; x++) {
            cardStack.add(new Card(colors[getRandom()], animals[getRandom()]));
        }
    }

    public ArrayList<Card> getCardStack() {
        return cardStack;
    }

    private int getRandom() {
        return (int) (Math.random()*4);
    }

}
