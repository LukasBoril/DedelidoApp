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
import com.example.myapplication.databinding.FragmentFifthBinding
import java.lang.Thread.sleep
import java.util.*
import kotlin.collections.ArrayList


/**
 * Fifth fragment class
 * This fragment is the actual "game GUI", where three cards are presented to the GUI
 * The current player needs to make a statement within 5 sec. The leftover time is displayed in real-time
 * The other player need to evaluate the made statement. If the current player did a mistake, they need to push the "wrong" button
 *
 * @author Lukas Boril
 * @version 2021.06.11
 */
class FifthFragment : Fragment() {

    private var _binding: FragmentFifthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFifthBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set view to landscape
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        // link the timer CountDownViewModel to the textView
        val modelTime: CountDownViewModel by activityViewModels()
        modelTime.leftOverTime.observe(viewLifecycleOwner, { newVal ->
            // update UI
            binding.f5TimerTextView.text = newVal.toString()
        })

        // Setting up a timer that counts down from 10, but displays a count down from 5
        var timePassed= 0
        val timer = object: CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                modelTime.leftOverTime.value = (5-timePassed)
                timePassed++
            }

            // once it's done, inform the backend that no mistake was made and that it's the next players turn
            override fun onFinish() {
                timePassed= 0
                val requestQueue = Volley.newRequestQueue(requireContext())

                var request = StringRequest(
                    Request.Method.PUT, "http://10.0.2.2:8080/roundCounter",
                    {
                    },
                    {
                        //use the provided VolleyError to display
                        //an error message
                        Log.e("ERROR", it.message!! )
                    })
                requestQueue.add(request)

                request = StringRequest(
                    Request.Method.GET, "http://10.0.2.2:8080/next",
                    {
                    },
                    {
                        //use the provided VolleyError to display
                        //an error message
                        Log.e("ERROR", it.message!! )
                    })
                requestQueue.add(request)

                // gives time for the backend
                sleep(1000)

                // navigate back to next-player fragment
                view.post {
                    findNavController().navigate(R.id.action_fifthFragment_to_FourthFragment)
                }
            }
        }
        // start the timer
        timer.start()

        // Binding of the button and setting it up. If it gets pushed the current player is punished and it is navigated to fragment6
        binding.f5Button.setOnClickListener {
            // REQUEST: tell backend to punish current player
            val requestQueue = Volley.newRequestQueue(requireContext())
            val request = StringRequest(
                Request.Method.GET, "http://10.0.2.2:8080/fail",
                {
                },
                {
                    //use the provided VolleyError to display
                    //an error message
                    Log.e("ERROR", it.message!! )
                })
            requestQueue.add(request)
            timer.cancel()
            view.post {
                findNavController().navigate(R.id.action_fifthFragment_to_sixthFragment)
            }
        }

        // binding of textViews for cards
        val viewCard1 = binding.f5ImageView

        val viewCard2 = binding.f5ImageView2

        val viewCard3 = binding.f5ImageView3

        // getting new cards and displaying them in ImageView 0 to 2
        val urlRoot = "com.example.myapplication:drawable/"
        val requestQueue = Volley.newRequestQueue(requireContext())
        val request = StringRequest(
            Request.Method.GET, "http://10.0.2.2:8080/openCards",
            { response ->
                val allOpenCards = ArrayList(Klaxon().parseArray<Card>(response))
                val addressFirstCard = produceCardAccessString(allOpenCards[0])
                var id = resources.getIdentifier(urlRoot + addressFirstCard, null, null)
                viewCard1.setImageResource(id)

                val addressSecondCard = produceCardAccessString(allOpenCards[1])
                id = resources.getIdentifier(urlRoot + addressSecondCard, null, null)
                viewCard2.setImageResource(id)

                val addressThirdCard = produceCardAccessString(allOpenCards[2])
                id = resources.getIdentifier(urlRoot + addressThirdCard, null, null)
                viewCard3.setImageResource(id)
            },
            {
                //use the provided VolleyError to display
                //an error message
                Log.e("ERROR", it.message!! )
            })
        requestQueue.add(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // function to produce strings used to access the drawable
    private fun produceCardAccessString(card : Card): String {
        val cardColor = card.getCardColor()
        val cardAnimal = card.getCardAnimal()
        val output = cardAnimal + "_" + cardColor
        return output.lowercase(Locale.getDefault())
    }

}