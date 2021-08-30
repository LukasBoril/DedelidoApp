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
import java.lang.Thread.sleep
import java.util.*
import kotlin.concurrent.schedule


/**
 * A simple [Fragment] subclass as the landing destination in the navigation after a mistake was
 * was made and the "Wrong" button was pressed.
 *  The screen is fixed to landscape orientation for this fragment.
 * Author: Nadine Duss
 * Version: 28.06.2021
 */
class SixthFragment : Fragment() {

    private var _binding: FragmentSixthBinding? = null
    private var backendPlayerController = BackendPlayerController()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
   
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

        // prepare the bindinig for the text and button components
       // var displayCurrentPlayer = binding.textViewF6
        val buttonContinue = binding.buttonContinueF6
        val buttonExit = binding.buttonExitF6

        buttonExit.setEnabled(false)
        buttonContinue.setEnabled(false)

        var displaytext = backendPlayerController.currentPlayer.name + " made a mistake.."
        binding.textViewF6.text = displaytext
        var newHealthpoint = backendPlayerController.currentPlayer.healthPoints
        if (newHealthpoint<1) {
            Timer().schedule(2000) {
                view?.post { findNavController().navigate(R.id.action_sixthFragment_to_eighthFragment) }
            }
        }
        else {
            buttonExit.setEnabled(true)
            buttonContinue.setEnabled(true)
            buttonContinue.setOnClickListener {
                backendPlayerController.next()
                findNavController().navigate(R.id.action_sixthFragment_to_fourthFragment)
            }
        }
        buttonExit.setOnClickListener {
            view?.post { findNavController().navigate(R.id.action_sixthFragment_to_seventhFragment) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
