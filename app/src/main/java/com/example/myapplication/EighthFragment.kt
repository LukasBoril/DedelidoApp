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
 * A simple [Fragment] subclass as the second destination in the navigation.
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
        get a list of all current players from the backend, including their name and healthpoints
        the name and the healthpoints are then bound to the Listview
         */
        val requestQueue = Volley.newRequestQueue(requireContext())
        requestQueue.add(getAllPlayers())

        adapter = F7PlayerScoreAdapter(allPlayers, requireContext())
        binding.listViewF8.adapter = adapter

// trial 1
        //requestQueue.add(showDeadPlayer())


        //trial 2 using the list from the adapter -> does not work
        /*
        var deadPlaya = adapter!!.getPlayerOnTurn()
        if (deadPlaya != null) {

            val displayText = "Game over! " + deadPlaya!!.getPlayerName() + " just died..."
            binding.textDeadF8.text = displayText
        }

        /*
        as we already have updated the adapter list, we use it to determine the player with the most remaining healthpoints
         */
        var winner = adapter!!.mostHealthpoint()
        if (winner != null) {
            var displayTextWinner = winner.getPlayerName() + " won with " + winner.getPlayerHealthPoints() + " HP left. Congrats!"
            binding.textWinnerF8.text = displayTextWinner
        }

*/

        binding.buttonExitF8.setOnClickListener {
            findNavController().navigate(R.id.action_eighthFragment_to_FirstFragment)
        }

        // trial 3: again direkt request

        val url = "http://10.0.2.2:8080/whosturn/"

        val request = StringRequest(
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
        requestQueue.add(request)






    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

   fun showDeadPlayer() : StringRequest {
       val url = "http://10.0.2.2:8080/whosturn"

       //define a request.

       val request = StringRequest(
           Request.Method.GET, url,
           Response.Listener<String> { response ->
               var deadPlayer = Klaxon().parse<CurrentPlayer>(response)

               if (deadPlayer != null)
               {
                   val displayText = "Game over! " + deadPlayer.getPlayerName() + " just died..."
                   binding.textDeadF8.text = displayText
               }
           },

           Response.ErrorListener {
               //use the porvided VolleyError to display
               //an error message
               Log.e("ERROR", it.message!!)
           })

       return request

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



