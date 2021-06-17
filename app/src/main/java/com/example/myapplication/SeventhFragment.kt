package com.example.myapplication

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.example.myapplication.databinding.FragmentSeventhBinding


/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SeventhFragment : Fragment() {

    private var _binding: FragmentSeventhBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSeventhBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var anyDeadPlayer = deadPlayer()
        if (anyDeadPlayer != null) {

            binding.textDeadF7.text = "Oh no, " + anyDeadPlayer.getPlayerName() + " just died!"
        }
/*
        binding.buttonExitF7.setOnClickListener {
            findNavController().navigate(R.id.action_seventhFragment_to_FirstFragment)


        }

 */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    public fun deadPlayer(): CurrentPlayer? {
        var players : MutableList<CurrentPlayer>? = null
            var dead = false
        var deadPlayer : CurrentPlayer? = null

        //  get players from backend into players

        val requestQueue = Volley.newRequestQueue(requireContext())

        //define a request.

        val request = StringRequest(
            Request.Method.GET, "http://10.0.2.2:8080/players",
            Response.Listener<String> { response ->
                players = Klaxon().parse<MutableList<CurrentPlayer>>(response)
            },

            Response.ErrorListener {
                //use the porvided VolleyError to display
                //an error message
                Log.e("ERROR", it.message!!)
            })

           requestQueue.add(request)

            if (players != null) {
                for (player in players!!) {
                var temp = player.getPlayerHealthPoints()
                if (temp >= 0) {
                dead = true
                 }
                    if (dead) {
                     deadPlayer = player
                      }
                }
            }
        return deadPlayer
    }
/*
    fun printScore() {
        var players : MutableList<CurrentPlayer>
        //  get players from backend into players

        for (player in players) {

            //TODO map player name and HP to layout...evtl with cells?
        }

    }

 */
/*
    fun callFromOtherFragment() {
        val fm: FragmentManager? = fragmentManager
        val fragm: SixthFragment = fm?.findFragmentById(R.id.sixth_frag_tag) as SixthFragment
        fragm.getCurrentPlayer()
    }

 */
}