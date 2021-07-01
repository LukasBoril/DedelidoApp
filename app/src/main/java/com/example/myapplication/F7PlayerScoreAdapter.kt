package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.myapplication.databinding.ScoreCellBinding

/**
* Adapter class that extends the [BaseAdapter]* in order to display a list of
 * players with their name and score in a ListView.
 * Author: Nadine Duss
 * Version: 28.06.2021
*/


class F7PlayerScoreAdapter (
    var entries: MutableList<CurrentPlayer>,
    val context: Context
) : BaseAdapter() {
    var layoutInflater: LayoutInflater
    private var _binding: ScoreCellBinding? = null
    private val binding get() = _binding!!

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int { //number of elements to display
        return entries.size
    }

    override fun getItem(index: Int): CurrentPlayer { //item at index
        return entries.get(index)
    }

    override fun getItemId(index: Int): Long { //itemId for index
        return index.toLong()
    }


    override fun getView(
        index: Int, oldView: View?,
        viewGroup: ViewGroup?
    ): View {
        var view: View
        if (oldView == null) { //check if we get a view to recycle
            _binding = ScoreCellBinding.inflate(
                layoutInflater,
                viewGroup, false
            )
            view = binding.root
        } else {  //if yes, use the oldview
            view = oldView
        }
        val entry = getItem(index) //get the data for this index
        binding.nameCellF7.text = entry.name

        binding.scoreCellF7.text = entry.healthPoints.toString()
        return view
    }



    fun mostHealthpoint() : CurrentPlayer? {

        var tempHP = 0
        var winner : CurrentPlayer? = null
        for (player in entries) {
            if (player.getPlayerHealthPoints() > tempHP) {
                winner = player
                tempHP = player.getPlayerHealthPoints()
            }
        }
        return winner
    }

    fun getPlayerOnTurn() : CurrentPlayer? {

        var currentPlayer = entries.find {p -> p.getPlayerYourTurn() }
        return currentPlayer
    }
}