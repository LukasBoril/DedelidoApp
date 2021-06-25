package com.example.myapplication

/**
 * Model class for the open cards
 * This class is used to hold the current cards information received from the backend
 *
 * @author Lukas Boril
 * @version 2021.06.19
 */

class OpenCards(val cards : MutableList<Card>) {
    fun getFirstCard(): Card {return cards.get(0)}
    fun getSecondCard(): Card {return cards.get(1)}
    fun getThirdCard(): Card {return cards.get(2)}
}

/**
 * Model class for the single cards
 * This class provides two method to get the color and the animal of a specified card
 *
 * @author Lukas Boril
 * @version 2021.06.19
 */
class Card(val color : String, val animal : String) {
    fun getCardColor(): String {return color}
    fun getCardAnimal(): String {return animal}
}