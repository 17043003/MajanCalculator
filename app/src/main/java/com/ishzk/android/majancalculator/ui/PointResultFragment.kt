package com.ishzk.android.majancalculator.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ishzk.android.majancalculator.R
import com.ishzk.android.majancalculator.databinding.FragmentPointResultBinding

class PointResultFragment : Fragment() {
    private var _binding: FragmentPointResultBinding? = null
    private val binding: FragmentPointResultBinding get() = _binding!!

    private val viewModel: PointResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPointResultBinding.inflate(inflater, container, false)



        return binding.root
    }
}