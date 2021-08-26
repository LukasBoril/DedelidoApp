package com.example.myapplication

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private var backendPlayerController = BackendPlayerController()

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun doesNextCommandWork() {
        backendPlayerController.addNewPlayer("Lukas")
        backendPlayerController.addNewPlayer("Nadine")
        backendPlayerController.startTheGame()
        assertEquals(backendPlayerController.currentPlayer.name, "Lukas")
        backendPlayerController.next()
        assertEquals(backendPlayerController.currentPlayer.name, "Nadine")

    }
}