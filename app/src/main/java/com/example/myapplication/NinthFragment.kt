package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment

import androidx.navigation.fragment.findNavController

import com.example.myapplication.databinding.FragmentNinthBinding

/**
 * A simple [Fragment] subclass with information and instructions about the Game
 * author: Nadine Duss
 * version: 26.06.2021
 */
class NinthFragment : Fragment() {

    private var _binding: FragmentNinthBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNinthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonExitF9.setOnClickListener {
            findNavController().navigate(R.id.action_ninthFragment_to_FirstFragment)
        }
            }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}