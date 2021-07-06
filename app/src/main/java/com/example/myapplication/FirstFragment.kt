package com.example.myapplication

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentFirstBinding
import kotlin.system.exitProcess
import androidx.appcompat.app.AppCompatActivity

/**
 * A simple [Fragment] subclass for beginning of the app.
 * The screen is fixed to portrait orientation for this fragment.
 * author: Kaltrim Bajrami
 * version: 06.07.2021
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set view to portrait
        getActivity()?.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Button to start the game, goes from first to third fragment
        binding.buttonStartgameF1.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ThirdFragment)
        }

        //Button to show about information, goes from first to ninth fragment
        binding.buttonAboutF1.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_ninthFragment)
        }
        //Exit button to quit app
        binding.buttonExitF1.setOnClickListener {
            MainActivity().quitApp()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}