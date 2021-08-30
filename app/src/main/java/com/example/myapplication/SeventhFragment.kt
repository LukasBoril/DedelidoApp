package com.example.myapplication


import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.example.myapplication.databinding.FragmentSeventhBinding


/**
 * A simple [Fragment] subclass as the destination before leaving the game.
 * The highscore of the current game is displayed in a listview.
 * The screen is fixed to landscape orientation for this fragment.
 * author: Nadine Duss
 * version: 26.06.2021
 */
class SeventhFragment : Fragment() {

    private var _binding: FragmentSeventhBinding? = null
    var backendPlayerController = BackendPlayerController()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var adapter: F7PlayerScoreAdapter? = null
    private var allPlayers = mutableListOf<CurrentPlayer>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSeventhBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        get a list of all current players from the backend, including their name and healthpoints
        the name and the healthpoints are then bound to the Listview
         */
        adapter = F7PlayerScoreAdapter(allPlayers, requireContext())
        binding.listViewF7.adapter = adapter

        var backendPlayerList = backendPlayerController.allPlayers
        for (p in backendPlayerList) {
            var newP : CurrentPlayer = CurrentPlayer(p.name, p.id, p.healthPoints, p.yourTurn)
            allPlayers.add(newP)
        }

        // allPlayers.addAll(players!!)
        adapter?.notifyDataSetChanged()

        var tempHP = 0
        var winner: CurrentPlayer? = null
        for (player in allPlayers) {
            if (player.getPlayerHealthPoints() > tempHP) {
                winner = player
                tempHP = player.getPlayerHealthPoints()
            }
        }

        if (winner != null) {
            var displayTextWinner =
                winner.getPlayerName() + " won with " + winner.getPlayerHealthPoints() + " HP left. Congrats!"
            binding.textWinnerF7.text = displayTextWinner
        }

        binding.buttonExitF7.setOnClickListener {
            //request to the backend ro reset the counter to 1 and the players to null/delete
            //to be ready for a new game

            backendPlayerController.clearPlayerList()
            findNavController().navigate(com.example.myapplication.R.id.action_seventhFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
