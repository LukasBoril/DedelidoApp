package com.example.myapplication

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentFifthBinding

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
            findNavController().navigate(R.id.action_fifthFragment_to_fourthFragment)
        }

        // Binding of the text field for the timer
        var lefttime = binding.f5TimerTextView

        // Setting up a timer that counts down from 10
        var timePassed= 0
        val timer = object: CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                lefttime.text = (5-timePassed).toString()
                timePassed++
            }

            override fun onFinish() {
                timePassed= 0
                view?.post {
                    findNavController().navigate(R.id.action_fifthFragment_to_fourthFragment)
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