package com.example.myapplication

class OpenCards(val cards : MutableList<Card>) {
    fun getFirstCard(): Card {return cards.get(0)}
    fun getSecondCard(): Card {return cards.get(1)}
    fun getThirdCard(): Card {return cards.get(2)}
}

class Card(val color : String, val animal : String) {
    fun getCardColor(): String {return color}
    fun getCardAnimal(): String {return animal}
}