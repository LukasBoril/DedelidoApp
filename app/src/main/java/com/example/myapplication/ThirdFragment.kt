package com.example.myapplication

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.example.myapplication.databinding.FragmentThirdBinding
import com.google.android.material.snackbar.Snackbar
import kotlin.collections.HashMap
import kotlin.collections.Map
import kotlin.collections.MutableMap
import kotlin.collections.set


/**
 * A simple [Fragment] subclass as first step of starting game, register the players.
 * Added players will be shown in a listview.
 * author: Kaltrim Bajrami
 * version: 06.07.2021
 */


class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    //playerlist for ListView in Fragment
    private var playerList = mutableListOf<CurrentPlayer>()

    //Adapter for ListView
    private var adapter: PlayerAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // make sure that the playerlist is cleared so that a new list with new players can be created
        clearPlayerList()


        super.onViewCreated(view, savedInstanceState)
        val requestQueue = Volley.newRequestQueue(requireContext())

        //adapter and binding for listview playerlist
        adapter = PlayerAdapter(playerList, requireContext())
        binding.listPlayersF3.adapter = adapter

        //post request on playerlist, name will be set like text content
        binding.buttonAddplayerF3.setOnClickListener {
            val postRequest = StringRequest(
                Request.Method.POST,
                "http://10.0.2.2:8080/players/" + binding.textInputplayersF3.text.toString(),
                Response.Listener<String> {
                },
                Response.ErrorListener {
                    //use the porvided VolleyError to display
                    //an error message
                    Log.e("ERROR", it.message!!)

                })
            //clears text after button click
            binding.textInputplayersF3.text.clear()

            requestQueue.add(postRequest)


            //get request to gest added players and show it in the listview
            val getRequest = StringRequest(
                Request.Method.GET, "http://10.0.2.2:8080/players/",
                Response.Listener<String> { response ->
                    var players = ArrayList(Klaxon().parseArray<CurrentPlayer>(response))
                    playerList.addAll(players!!)
                    adapter?.notifyDataSetChanged()

                },
                Response.ErrorListener {
                    //use the porvided VolleyError to display
                    //an error message
                    Log.e("ERROR", it.message!!)
                })
            requestQueue.add(getRequest)
        }

        binding.buttonStartgameF3.setOnClickListener {
            //first checks if player is registered, then game will starts
            if (playerList.isNotEmpty()) {
                val getRequest = StringRequest(
                    Request.Method.GET, "http://10.0.2.2:8080/start/",
                    Response.Listener<String> {

                    },
                    Response.ErrorListener {

                        Log.e("ERROR", it.message!!)
                    })
                requestQueue.add(getRequest)
                view?.post {
                    findNavController().navigate(R.id.action_ThirdFragment_to_secondFragment)
                }
                //otherwise a message appears
            } else {
                Snackbar.make(
                    view,
                    "No player registered. Please register player first!",
                    Snackbar.LENGTH_LONG
                ).setAction("Action", null).show()


            }

        }

        fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }

    /**
     * Method to clear playerList
     */
    private fun clearPlayerList() {
        if (playerList.isNotEmpty()) {
            playerList.clear()
        }
    }
}



