package com.ishzk.android.majancalculator.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.ishzk.android.majancalculator.databinding.FragmentHandPointBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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

        with(binding){
            selectManzuButtons.forEach { it.setOnClickListener{ view -> viewModel?.onClickImageButton(view) } }
            selectSouzuButtons.forEach { it.setOnClickListener{ view -> viewModel?.onClickImageButton(view) } }
            selectPinzuButtons.forEach { it.setOnClickListener{ view -> viewModel?.onClickImageButton(view) } }
            selectHonorsButtons.forEach { it.setOnClickListener{ view -> viewModel?.onClickImageButton(view) } }
        }

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.closeTiles.collect{
                    if(it.size() != 0) {
                        val tile = it.tiles.last().toString()

                    }
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}