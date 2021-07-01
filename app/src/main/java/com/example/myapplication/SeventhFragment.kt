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
        //val winnerBind = binding.textWinnerF7

        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(getAllPlayers())

        adapter = F7PlayerScoreAdapter(allPlayers, requireContext())
        binding.listViewF7.adapter = adapter

        binding.buttonExitF7.setOnClickListener {
            //request to the backend ro reset the counter to 1 and the players to null/delete
            //to be ready for a new game
            val url = "http://10.0.2.2:8080/clear/"
            val request = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> {},

                Response.ErrorListener {
                    //use the porvided VolleyError to display
                    //an error message
                    Log.e("ERROR", it.message!!)
                })
            requestQueue.add(request)
            findNavController().navigate(com.example.myapplication.R.id.action_seventhFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /*
    * Method to request a list of all players of the current game from the backend.
    * The winner is evaluated and displayed in a separate textView.
    * all other players' name and score is displayed as a listView.
    * return: a Volley StringRequest
     */
    fun getAllPlayers(): StringRequest {
        //var players : MutableList<CurrentPlayer>? = null
        //  var dead = false
        //  var deadPlayer : CurrentPlayer? = null
        val url = "http://10.0.2.2:8080/players/"


        //define a request.

        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                var players = ArrayList(Klaxon().parseArray<CurrentPlayer>(response))
                allPlayers.addAll(players!!)
                adapter?.notifyDataSetChanged()

                // evaluate the winner. If players have an identical score, the first player
                // encountered will be displayed.
                var tempHP = 0
                var winner: CurrentPlayer? = null
                for (player in players) {
                    if (player.getPlayerHealthPoints() > tempHP) {
                        winner = player
                        tempHP = player.getPlayerHealthPoints()
                    }
                }

                if (winner != null) {
                    var displayTextWinner =
                        winner.getPlayerName() + " won with " + winner.getPlayerHealthPoints() + " HP left. Congrats!"
                    //binding.textWinnerF7.text = displayTextWinner
                    binding.textWinnerF7.text = displayTextWinner
                }
            },

            Response.ErrorListener {
                //use the porvided VolleyError to display
                //an error message
                Log.e("ERROR", it.message!!)
            })

        return request
    }
}
