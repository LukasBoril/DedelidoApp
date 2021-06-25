package com.example.myapplication

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.example.myapplication.databinding.FragmentFourthBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class FourthFragment : Fragment() {

    private var _binding: FragmentFourthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFourthBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set view to landscape
        getActivity()?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

        // Binding of the button
        binding.f4ButtonBackToMain.setOnClickListener {
            findNavController().navigate(R.id.action_FourthFragment_to_FirstFragment)
        }

        // Binding of the text field for the timer
////// Question: if I do this in the onTick method directly, it does not work... why?
        var lefttime = binding.f4TextView3

        val model: CurrentPlayerViewModel by activityViewModels()
        model.name.observe(viewLifecycleOwner, Observer<String>{newVal ->
            // update UI
            binding.f4TextView2.text = newVal
        })

            // REQUEST: ask backend who's turn it is
        val requestQueue = Volley.newRequestQueue(requireContext())
        val request2 = StringRequest(
            Request.Method.GET, "http://10.0.2.2:8080/whosturn",
            Response.Listener<String> { response ->
                val nextPlayer = Klaxon().parse<CurrentPlayer>(response)
                model.name.value = nextPlayer?.getPlayerName()
            },
            Response.ErrorListener {
                //use the porvided VolleyError to display
                //an error message
                Log.e("ERROR", it.message!! )
            })
        requestQueue.add(request2)


        // Setting up a timer that counts down from 10
        var timePassed= 0
        val timer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                lefttime.text = (10-timePassed).toString()
                timePassed++
            }

            override fun onFinish() {
                timePassed= 0
                view?.post {
                    findNavController().navigate(R.id.action_FourthFragment_to_fifthFragment)
                }
            }
        }
        timer.start()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}