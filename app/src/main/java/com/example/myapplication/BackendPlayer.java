package com.example.myapplication;

public class BackendPlayer {

    private String name;
    private Integer id;
    private static Integer idCounter = 0;
    private Integer healthPoints;
    private Boolean yourTurn;

    public BackendPlayer(String name) {
        setName(name);
        setId(++idCounter);
        setHealthPoints(30);
        setYourTurn(false);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(Integer healthPoints) {
        this.healthPoints = healthPoints;
    }

    public Boolean getYourTurn() {
        return yourTurn;
    }

    public void setYourTurn(Boolean yourTurn) {
        this.yourTurn = yourTurn;
    }


}


