package com.ishzk.android.majancalculator.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ishzk.android.majancalculator.R
import com.ishzk.android.majancalculator.databinding.FragmentPointResultBinding

class PointResultFragment : Fragment() {
    private var _binding: FragmentPointResultBinding? = null
    private val binding: FragmentPointResultBinding get() = _binding!!

    private val args: PointResultFragmentArgs by navArgs()

    private val viewModel: PointResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPointResultBinding.inflate(inflater, container, false)

        binding.FannResult.text = args.resultHandPoint.toString()

        return binding.root
    }
}