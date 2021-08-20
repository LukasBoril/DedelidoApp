package com.example.myapplication;

import java.util.ArrayList;

public class BackendCardController {

    private BackendCardService service = new BackendCardService();

    public ArrayList<Card> getOpenCards() {
        return service.getCardStack();
    }
}
