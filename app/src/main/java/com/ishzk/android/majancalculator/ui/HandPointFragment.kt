package com.ishzk.android.majancalculator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ishzk.android.majancalculator.databinding.FragmentHandPointBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HandPointFragment: Fragment() {
    private var _binding: FragmentHandPointBinding? = null
    private val binding: FragmentHandPointBinding get() = _binding!!

    private val viewModel: HandPointViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHandPointBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}