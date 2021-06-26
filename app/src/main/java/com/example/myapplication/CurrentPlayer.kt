package com.example.myapplication

class CurrentPlayer(val name : String, val id: Int, val healthPoints : Int, var yourTurn : Boolean) {

    fun getPlayerName(): String {return name}

    fun getPlayerHealthPoints(): Int {return healthPoints}

}

