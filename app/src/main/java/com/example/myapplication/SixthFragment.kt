package com.example.myapplication

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.myapplication.databinding.FragmentSixthBinding
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import java.util.*
import kotlin.concurrent.schedule

class MyInt (var i : Int) {fun getInt(): Int {return i}}

/**
 * A simple [Fragment] subclass as the landing destination in the navigation after a mistake was
 * was made and the "Wrong" button was pressed.
 *  The screen is fixed to landscape orientation for this fragment.
 * Author: Nadine Duss
 * Version: 28.06.2021
 */
class SixthFragment : Fragment() {

    private var _binding: FragmentSixthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var currentPlayer: CurrentPlayer? = null

    //private var adapter: F7PlayerScoreAdapter? = null
    //  private var allPlayers = mutableListOf<CurrentPlayer>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        getActivity()?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        _binding = FragmentSixthBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        // Request current player from the background and display his name.
        // The current Player conducted the mistake.

        var displayCurrentPlayer = binding.textViewF6

        val requestQueue = Volley.newRequestQueue(requireContext())
        // requestQueue.add(getCurrentPlayer(displayCurrentPlayer))

        val buttonContinue = binding.buttonContinueF6
        val buttonExit = binding.buttonExitF6

        buttonExit.setEnabled(false)
        buttonContinue.setEnbled(false)

        val url = "http://10.0.2.2:8080/whosturn/"

        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val tempcurrentPlayer = Klaxon().parse<CurrentPlayer>(response)

                if (tempcurrentPlayer != null) {
                    //display name of current player
                    val displayText =
                        tempcurrentPlayer.getPlayerName().toString() + " made a mistake.."
                    displayCurrentPlayer.text = displayText
                    currentPlayer = tempcurrentPlayer //doe3sn't work!!!

                    //evaluate healthpoints of current player. Is he still alive?
                    //alive: enbale buttons continue and exit
                    // dead: transit automatically to fragment 8
                    val hp = tempcurrentPlayer.getPlayerHealthPoints()
                    if (hp < 1) {
                        Timer().schedule(2000) {
                            view?.post { findNavController().navigate(R.id.action_sixthFragment_to_eighthFragment) }
                        }
                    } else {
                        buttonExit.setEnabled(true)
                        buttonContinue.setEnabled(true)
                        buttonContinue.setOnClickListener {
                            val request = StringRequest(
                                Request.Method.GET, "http://10.0.2.2:8080/next",
                                Response.Listener<String> {
                                },
                                Response.ErrorListener {
                                    //use the porvided VolleyError to display
                                    //an error message
                                    Log.e("ERROR", it.message!!)
                                })
                            requestQueue.add(request)
                            findNavController().navigate(R.id.action_sixthFragment_to_fourthFragment)
                        }
                        buttonExit.setOnClickListener {
                            view?.post { findNavController().navigate(R.id.action_sixthFragment_to_seventhFragment) }

                        }

                    }
                }
            },
            Response.ErrorListener {
                //use the porvided VolleyError to display
                //an error message
                Log.e("ERROR", it.message!!)
                print("request \"whosturn\" to the backend failed.")
            })

        requestQueue.add(request)


        //Timer().schedule(2000) {
        // var hp = currentPlayer!!.getPlayerHealthPoints()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
/*
    fun getCurrentPlayer(displayTextView : TextView) : StringRequest {
        val url = "http://10.0.2.2:8080/whosturn/"

        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val tempcurrentPlayer = Klaxon().parse<CurrentPlayer>(response)

                if (tempcurrentPlayer != null)
                {
                    val displayText = tempcurrentPlayer.getPlayerName().toString() + " made a mistake.."
                    displayTextView.text = displayText
                    currentPlayer = tempcurrentPlayer //doe3sn't work!!!

                }
            },

            Response.ErrorListener {
                //use the porvided VolleyError to display
                //an error message
                Log.e("ERROR", it.message!!)
            })
        return request

 */

//
//    fun checkPlayerAlive() {
//        val url = "http://10.0.2.2:8080/alive"
//        var alive : Boolean? = true
//        val requestQueue = Volley.newRequestQueue(requireContext())
//
//        val request = StringRequest(
//            Request.Method.GET, url,
//            Response.Listener<String> { response ->
//                val currentPlayerAlive = Klaxon().parse<Boolean>(response)
//                if (currentPlayerAlive != null) {
//                    this.currentPlayerAlive = currentPlayerAlive
//                }
//            },
//
//            Response.ErrorListener {
//                //use the porvided VolleyError to display
//                //an error message
//                Log.e("ERROR", it.message!!)
//            })
//
//        requestQueue.add(request)
//
//
//    }

    //original method
//    fun checkPlayerAlive() : Boolean? {
//        val url = "http://10.0.2.2:8080/alive/"
//        var alive : Boolean? = true
//        val requestQueue = Volley.newRequestQueue(requireContext())
//
//        val request = StringRequest(
//            Request.Method.GET, url,
//            Response.Listener<String> { response ->
//                val currentPlayerAlive = Klaxon().parse<Boolean>(response)
//                alive = currentPlayerAlive
//            },
//
//            Response.ErrorListener {
//                //use the porvided VolleyError to display
//                //an error message
//                Log.e("ERROR", it.message!!)
//            })
//
//        requestQueue.add(request)
//        return alive
//
//    }



// view?.post { findNavController.....}