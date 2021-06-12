package com.example.myapplication

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
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
            findNavController().navigate(R.id.action_fourthFragment_to_FirstFragment)
        }

        // Binding of the text field for the timer
////// Question: if I do this in the onTick method directly, it does not work... why?
        var lefttime = binding.f4TextView3


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
                    findNavController().navigate(R.id.action_fourthFragment_to_fifthFragment)
                }
            }
        }
        timer.start()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun Fragment.findNavControllerSafely(): NavController? {
        return if (isAdded) {
            findNavController()
        } else {
            null
        }
    }

}