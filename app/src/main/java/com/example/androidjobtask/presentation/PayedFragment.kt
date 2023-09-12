package com.example.androidjobtask.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.androidjobtask.R
import com.example.androidjobtask.databinding.FragmentPayedBinding

class PayedFragment : Fragment() {

    private lateinit var binding : FragmentPayedBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPayedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.apply {
            finish.setBackgroundColor(Color.parseColor("#0D72FF"))
            finish.setOnClickListener {
                findNavController().navigate(R.id.action_payedFragment_to_mainFragment)
            }
            back.setOnClickListener{
                requireActivity().onBackPressed()
            }
        }
    }



    companion object {
        @JvmStatic
        fun newInstance() = PayedFragment()
    }
}