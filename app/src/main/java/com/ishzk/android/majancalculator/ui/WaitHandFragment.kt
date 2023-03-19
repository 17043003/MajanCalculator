package com.ishzk.android.majancalculator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ishzk.android.majancalculator.databinding.FragmentWaitHandBinding

class WaitHandFragment : Fragment() {
    private val viewModel: WaitHandViewModel by viewModels()
    private var _binding: FragmentWaitHandBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWaitHandBinding.inflate(layoutInflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}