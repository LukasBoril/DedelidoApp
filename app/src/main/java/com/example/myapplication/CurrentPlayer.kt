package com.example.myapplication

/**
* Basic Model class defining a player including player name and health points
 */
class CurrentPlayer(val name : String, val id: Int, val healthPoints : Int, var yourTurn : Boolean) {
    fun getPlayerName(): String {return name}

    public fun getPlayerHealthPoints(): Int {return healthPoints}

    public fun getPlayerYourTurn() : Boolean {return yourTurn}

}