package com.ishzk.android.majancalculator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ishzk.android.majancalculator.databinding.FragmentWaitHandBinding
import com.ishzk.android.majancalculator.ui.adapter.SelectHandAdapter

class WaitHandFragment : Fragment() {
    private val viewModel: WaitHandViewModel by viewModels()
    private var _binding: FragmentWaitHandBinding? = null
    private val binding get() = _binding!!

    private val adapter by lazy { SelectHandAdapter(viewModel) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWaitHandBinding.inflate(layoutInflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.selectedHandsList.adapter = adapter
        binding.selectedHandsList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        lifecycleScope.launchWhenStarted {
            viewModel.closeTiles.observe(viewLifecycleOwner){
                adapter.submitList(it.splitByKindWithPrefix())
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
