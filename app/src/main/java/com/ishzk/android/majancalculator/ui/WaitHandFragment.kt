package com.ishzk.android.majancalculator.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
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

            val listener = { button: View ->
                val hand = viewModel.getIDString(button.id)
                Log.d(TAG, "$hand long tapped.")
                true
            }

            // set long click listener to all image button.
            with(binding) {
                linearLayout.forEach { it.setOnLongClickListener(listener) }
                linearLayout2.forEach { it.setOnLongClickListener(listener) }
                linearLayout3.forEach { it.setOnLongClickListener(listener) }
                linearLayout4.forEach { it.setOnLongClickListener(listener) }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "WaitHandFragment"
    }
}
