package com.example.myapplication

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.example.myapplication.databinding.FragmentFourthBinding

/**
 * Fourth fragment class
 * This fragment is the "get ready" view, where the players are Ninformed, who is up next
 * The leftover time until the next turn starts is displayed in real-time
 *
 * @author Lukas Boril
 * @version 2021.06.11
 */
class FourthFragment : Fragment() {

    private var _binding: FragmentFourthBinding? = null
    private var playerControler = BackendPlayerControler()

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
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // Binding of the button. This will abort the current game
        binding.f4ButtonBackToMain.setOnClickListener {
            findNavController().navigate(R.id.action_FourthFragment_to_seventhFragment)
        }

        // Binding of the text field for the timer and linking it to the CountDownViewModel
        val modelTime: CountDownViewModel by activityViewModels()
        modelTime.leftOverTime.observe(viewLifecycleOwner, { newVal ->
            // update UI
            binding.f4TextView3.text = newVal.toString()
        })

        // Binding of the text field for the current player and linking it to the CurrentPlayerViewModel
        val model: CurrentPlayerViewModel by activityViewModels()
        model.name.observe(viewLifecycleOwner, { newVal ->
            // update UI
            binding.f4TextView2.text = newVal
        })

        // REQUEST: ask backend who's turn it is and update the CurrentPlayerViewModel accordingly
        val requestQueue = Volley.newRequestQueue(requireContext())
        val request2 = StringRequest(
            Request.Method.GET, "http://10.0.2.2:8080/whosturn",
            { response ->
                val nextPlayer = Klaxon().parse<CurrentPlayer>(response)
                model.name.value = nextPlayer?.getPlayerName()
            },
            {
                //use the provided VolleyError to display
                //an error message
                Log.e("ERROR", it.message!! )
            })
        requestQueue.add(request2)

        // Setting up a timer that counts down from 10
        var timePassed= 0
        val timer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
            modelTime.leftOverTime.value = (10-timePassed)
                timePassed++
            }

            // once it's done, navigate to fragment 5
            override fun onFinish() {
                timePassed= 0
                view.post {
                    findNavController().navigate(R.id.action_FourthFragment_to_fifthFragment)
                }
            }
        }
        // Start the timer
        timer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}