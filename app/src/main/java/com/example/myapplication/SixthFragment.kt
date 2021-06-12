package com.example.myapplication

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.myapplication.databinding.FragmentSixthBinding
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon



/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SixthFragment : Fragment() {

    private var _binding: FragmentSixthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var currentPlayer : CurrentPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        getActivity()?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        _binding = FragmentSixthBinding.inflate(inflater, container, false)

//get player
        val requestQueue = Volley.newRequestQueue(requireContext())

        //define a request.
        val request = StringRequest(
            Request.Method.GET, "http://localhost:8080/whosturn",
            Response.Listener<String> { response ->
                this.currentPlayer = Klaxon().parse<CurrentPlayer>(response)
            },

            Response.ErrorListener {
                //use the porvided VolleyError to display
                //an error message
                Log.e("ERROR", it.message!!)
            })


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
binding.textViewF6.text = "Wrong!\n" + currentPlayer?.getPlayerName() + " loses valuable points!"

        binding.buttonContinueF6.setOnClickListener {
            findNavController().navigate(R.id.action_sixthFragment_to_fourthFragment)
        }
        binding.buttonExitF6.setOnClickListener {
            findNavController().navigate(R.id.action_sixthFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}