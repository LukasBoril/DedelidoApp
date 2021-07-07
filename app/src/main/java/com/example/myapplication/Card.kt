package com.example.myapplication


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