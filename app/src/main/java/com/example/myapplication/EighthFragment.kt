package com.example.myapplication


import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.example.myapplication.databinding.FragmentEighthBinding


/**
 * A simple [Fragment] subclass as the destination if a player has no
 * healthpoints left after a mistake. The game is over as soon as the first player dies.
 * The screen is fixed to landscape orientation for this fragment.
 * author: Nadine Duss
 * Version: 28.06.2021
 */
class EighthFragment : Fragment() {

    private var _binding: FragmentEighthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var adapter: F7PlayerScoreAdapter? = null
    private var allPlayers = mutableListOf<CurrentPlayer>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEighthBinding.inflate(inflater, container, false)
        getActivity()?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        Get a list of all current players from the backend, including their name and healthpoints.
        The name and the healthpoints are then bound to the Listview
         */
        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(getAllPlayers())

        adapter = F7PlayerScoreAdapter(allPlayers, requireContext())
        binding.listViewF8.adapter = adapter

        binding.buttonExitF8.setOnClickListener {

            //request to the backend ro reset the counter to 1 and the players to null/delete
            //to be ready for a new game
            val url = "http://10.0.2.2:8080/clear/"
            val resetRequest = StringRequest(
                Request.Method.GET, url,
                Response.Listener<String> {},

                Response.ErrorListener {
                    //use the porvided VolleyError to display
                    //an error message
                    Log.e("ERROR", it.message!!)
                })
            requestQueue.add(resetRequest)
            findNavController().navigate(R.id.action_eighthFragment_to_FirstFragment)
        }

        // request and then display the current player on the move, as he is the one that just died.

        val url = "http://10.0.2.2:8080/whosturn/"

        val playerRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val tempcurrentPlayer = Klaxon().parse<CurrentPlayer>(response)
                if (tempcurrentPlayer != null)
                {
                    val displayText = tempcurrentPlayer.getPlayerName() + " just died!"
                    binding.textDeadF8.text = displayText
                }
            },

            Response.ErrorListener {
                //use the porvided VolleyError to display
                //an error message
                Log.e("ERROR", it.message!!)
            })
        requestQueue.add(playerRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun getAllPlayers() : StringRequest {

        val url = "http://10.0.2.2:8080/players/"

        //define a request.
        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
               var players = ArrayList(Klaxon().parseArray<CurrentPlayer>(response))

                allPlayers.addAll(players!!)
                adapter?.notifyDataSetChanged()

                var tempHP = 0
                var winner : CurrentPlayer? = null
                for (player in players) {
                    if (player.getPlayerHealthPoints() > tempHP) {
                        winner = player
                        tempHP = player.getPlayerHealthPoints()
                    }
                }
                binding.textWinnerF8.text = winner?.getPlayerName() + " won! congrats"
                                      },

            Response.ErrorListener {
                //use the porvided VolleyError to display
                //an error message
                Log.e("ERROR", it.message!!)
            })

        return request
    }
}



