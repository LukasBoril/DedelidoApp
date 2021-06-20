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
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SixthFragment : Fragment() {

    private var _binding: FragmentSixthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var currentPlayer : CurrentPlayer? = null
    // private var currentPlayerAlive : Boolean = true
    private var adapter: F7PlayerScoreAdapter? = null
    private var allPlayers = mutableListOf<CurrentPlayer>()


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

        //get player

        var displayCurrentPlayer = binding.textViewF6

        val requestQueue = Volley.newRequestQueue(requireContext())

        requestQueue.add(getCurrentPlayer(displayCurrentPlayer))

        var currenPlaya = adapter?.getPlayerOnTurn()

        //Timer().schedule(2000) {
           // var hp = currentPlayer!!.getPlayerHealthPoints()

            if (currenPlaya != null && currenPlaya!!.getPlayerHealthPoints() > 0) {
                binding.buttonContinueF6.setOnClickListener {
                    findNavController().navigate(R.id.action_sixthFragment_to_fourthFragment)
                }
                binding.buttonExitF6.setOnClickListener {
                    findNavController().navigate(R.id.action_sixthFragment_to_seventhFragment)
                }
            }
            else {
                findNavController().navigate(R.id.action_sixthFragment_to_eighthFragment)
            }
        //}

            /*
            Timer().schedule(2000) {
                val url = "http://10.0.2.2:8080/alive"
                val request = StringRequest(
                    Request.Method.GET, url,
                    Response.Listener<String> { response ->
                        //val currentPlayerAlive = Klaxon().parse<Integer>(response)
                        //binding.textViewF6.text = currentPlayerAlive.toString()
                        val currentPlayerAlive = Klaxon().parse<MyInt>(response)
                        //binding.textViewF6.text = currentPlayerAlive.toString()


                        if (currentPlayerAlive != null) {


                            if (currentPlayerAlive.getInt() == 0) {
                                //fragment 8
                                findNavController().navigate(R.id.action_sixthFragment_to_eighthFragment)
                            } else {
                                binding.buttonContinueF6.setOnClickListener {
                                    findNavController().navigate(R.id.action_sixthFragment_to_fourthFragment)
                                }
                                binding.buttonExitF6.setOnClickListener {
                                    findNavController().navigate(R.id.action_sixthFragment_to_seventhFragment)
                                }
                            }


                        }


                    },

                    Response.ErrorListener {
                        //use the porvided VolleyError to display
                        //an error message
                        Log.e("ERROR", it.message!!)
                    })

                requestQueue.add(request)
            }

    */


//
//        if (!currentPlayerAlive) {
//           //fragment 8
//            findNavController().navigate(R.id.action_sixthFragment_to_eighthFragment)
//        }
//        else {
//            binding.buttonContinueF6.setOnClickListener {
//                findNavController().navigate(R.id.action_sixthFragment_to_fourthFragment)
//            }
//            binding.buttonExitF6.setOnClickListener {
//                findNavController().navigate(R.id.action_sixthFragment_to_seventhFragment)
//            }
//        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun getCurrentPlayer(displayTextView : TextView) : StringRequest {
        val url = "http://10.0.2.2:8080/whosturn/"

        val request = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                val tempcurrentPlayer = Klaxon().parse<CurrentPlayer>(response)

                if (tempcurrentPlayer != null)
                {
                    val displayText = "oh no!\n" + tempcurrentPlayer.getPlayerName().toString() + " made a mistake.."
                    displayTextView.text = displayText
                    currentPlayer = tempcurrentPlayer //doe3sn't work!!!

                }
                allPlayers.addAll(mutableListOf(tempcurrentPlayer!!))
                adapter?.notifyDataSetChanged()
            },

            Response.ErrorListener {
                //use the porvided VolleyError to display
                //an error message
                Log.e("ERROR", it.message!!)
            })
        return request
    }
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
}