package com.ishzk.android.majancalculator.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ishzk.android.majancalculator.databinding.FragmentHandPointBinding
import com.ishzk.android.majancalculator.ui.listitem.CloseTileItem
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HandPointFragment: Fragment() {
    private var _binding: FragmentHandPointBinding? = null
    private val binding: FragmentHandPointBinding get() = _binding!!

    private val viewModel: HandPointViewModel by viewModels()

    private val closeTileAdapter by lazy { GroupieAdapter() }
    private val openTileAdapter by lazy { GroupieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHandPointBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.apply {
            selectedTilesList.adapter = closeTileAdapter
            selectedTilesList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            selectedOpenTilesList.adapter = openTileAdapter
            selectedOpenTilesList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            selectManzuButtons.forEach { it.setOnClickListener{ view -> viewModel?.onClickImageButton(view) } }
            selectSouzuButtons.forEach { it.setOnClickListener{ view -> viewModel?.onClickImageButton(view) } }
            selectPinzuButtons.forEach { it.setOnClickListener{ view -> viewModel?.onClickImageButton(view) } }
            selectHonorsButtons.forEach { it.setOnClickListener{ view -> viewModel?.onClickImageButton(view) } }
        }

        // Show selected close tiles.
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.closeTiles.collect{
                    if(it.size() != 0) {
                        Log.d("HandPointFragment", "clicked.")
                        closeTileAdapter.clear()
                        val itemList = it.tiles.map { tile ->
                            CloseTileItem(tile, viewModel.getDrawableID(tile.toString())){ viewModel.onClickSelectedCloseTile(tile) }
                        }
                        closeTileAdapter.addAll(itemList)
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