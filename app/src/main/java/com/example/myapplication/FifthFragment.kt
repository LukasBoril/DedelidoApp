package com.example.myapplication

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.beust.klaxon.Klaxon
import com.example.myapplication.databinding.FragmentFifthBinding
import kotlinx.coroutines.delay

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
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
        getActivity()?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // Binding of the button and setting it up
        binding.f5Button.setOnClickListener {
            // REQUEST: tell backend to get next player
            val requestQueue = Volley.newRequestQueue(requireContext())
            val request = StringRequest(
                Request.Method.GET, "http://10.0.2.2:8080/next",
                Response.Listener<String> {
                },
                Response.ErrorListener {
                    //use the porvided VolleyError to display
                    //an error message
                    Log.e("ERROR", it.message!! )
                })
            requestQueue.add(request)
            println("SUCHE MICH: REQUEST ABGESETZT")
            view?.post {
                findNavController().navigate(R.id.action_fifthFragment_to_FourthFragment)
            }
        }

        // Binding of the text field for the timer
        var lefttime = binding.f5TimerTextView

        // binding of textViews for cards
        var viewCard1 = binding.f5ImageView
        //var id = resources.getIdentifier("com.example.myapplication:drawable/penguin_blue", null, null)
        //iewCard1.setImageResource(id)

        var viewCard2 = binding.f5ImageView2
        //id = resources.getIdentifier("com.example.myapplication:drawable/penguin_blue", null, null)
        //viewCard2.setImageResource(id)

        var viewCard3 = binding.f5ImageView3
        //id = resources.getIdentifier("com.example.myapplication:drawable/penguin_blue", null, null)
        //viewCard3.setImageResource(id)

        // getting new cards and displaying them in ImageView 0 to 2
        val requestQueue = Volley.newRequestQueue(requireContext())
        val request = StringRequest(
            Request.Method.GET, "http://10.0.2.2:8080/openCards",
            Response.Listener<String> { response ->
                val allOpenCards = ArrayList(Klaxon().parseArray<Card>(response))
                if (allOpenCards != null) {
                    var addressFirstCard = produceCardAccessString(allOpenCards.get(0))
                    var id = resources.getIdentifier("com.example.myapplication:drawable/" + addressFirstCard, null, null)
                    viewCard1.setImageResource(id)

                    var addressSecondCard = produceCardAccessString(allOpenCards.get(1))
                    id = resources.getIdentifier("com.example.myapplication:drawable/" + addressSecondCard, null, null)
                    viewCard2.setImageResource(id)

                    var addressThirdCard = produceCardAccessString(allOpenCards.get(2))
                    id = resources.getIdentifier("com.example.myapplication:drawable/" + addressThirdCard, null, null)
                    viewCard3.setImageResource(id)
                }
            },
            Response.ErrorListener {
                //use the porvided VolleyError to display
                //an error message
                Log.e("ERROR", it.message!! )
            })
        requestQueue.add(request)



        // Setting up a timer that counts down from 10
        var timePassed= 0
        val timer = object: CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                lefttime.text = (5-timePassed).toString()
                timePassed++
            }

            // once it's done, inform the backend that no mistake was made
            override fun onFinish() {
                timePassed= 0
                val requestQueue = Volley.newRequestQueue(requireContext())

                var request = StringRequest(
                    Request.Method.PUT, "http://10.0.2.2:8080/roundCounter",
                    Response.Listener<String> {
                    },
                    Response.ErrorListener {
                        //use the porvided VolleyError to display
                        //an error message
                        Log.e("ERROR", it.message!! )
                    })
                requestQueue.add(request)

                request = StringRequest(
                    Request.Method.GET, "http://10.0.2.2:8080/next",
                    Response.Listener<String> {
                    },
                    Response.ErrorListener {
                        //use the porvided VolleyError to display
                        //an error message
                        Log.e("ERROR", it.message!! )
                    })
                requestQueue.add(request)

                // navigate back to next-player fragment
                view?.post {
                    findNavController().navigate(R.id.action_fifthFragment_to_FourthFragment)
                }
            }
        }
        timer.start()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun produceCardAccessString(card : Card): String {
        var cardColor = card.getCardColor()
        var cardAnimal = card.getCardAnimal()
        val output = cardAnimal + "_" + cardColor
        return output.toLowerCase()
    }

}