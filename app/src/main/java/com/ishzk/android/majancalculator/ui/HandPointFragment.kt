package com.ishzk.android.majancalculator.ui

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ishzk.android.majancalculator.databinding.FragmentHandPointBinding
import com.ishzk.android.majancalculator.domain.OpenTile
import com.ishzk.android.majancalculator.ui.listitem.ChiItem
import com.ishzk.android.majancalculator.ui.listitem.CloseTileItem
import com.ishzk.android.majancalculator.ui.listitem.ClosedKanItem
import com.ishzk.android.majancalculator.ui.listitem.OpenKanItem
import com.ishzk.android.majancalculator.ui.listitem.PonItem
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
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
            selectedOpenTilesList.addItemDecoration(OpenTileListDecoration())
            selectedOpenTilesList.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

            selectManzuButtons.forEach { it.setOnClickListener{ view -> viewModel?.onClickImageButton(view) } }
            selectSouzuButtons.forEach { it.setOnClickListener{ view -> viewModel?.onClickImageButton(view) } }
            selectPinzuButtons.forEach { it.setOnClickListener{ view -> viewModel?.onClickImageButton(view) } }
            selectHonorsButtons.forEach { it.setOnClickListener{ view -> viewModel?.onClickImageButton(view) } }
        }

        // Set toggle buttons listener.
        setToggleButtons()

        // Show selected close tiles on RecyclerView.
        setCloseTiles()

        // Show selected open tiles on RecyclerView.
        setOpenTiles()

        // Show selected won tile.
        showSelectedWonTile()

        // reach or double reach can be checked.
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.reachCheck.collect{
                    if(it) viewModel.doubleReachCheck.value = false
                    else viewModel.oneShotCheck.value = (viewModel.reachCheck.value || viewModel.doubleReachCheck.value) && viewModel.oneShotCheck.value
                }
            }
        }

        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.doubleReachCheck.collect{
                    if(it) viewModel.reachCheck.value = false
                    else viewModel.oneShotCheck.value = (viewModel.reachCheck.value || viewModel.doubleReachCheck.value) && viewModel.oneShotCheck.value
                }
            }
        }

        // disabled checkbox by selected tiles.
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isMenzen.collect {
                    if(!it) {
                        viewModel.reachCheck.value = false
                        viewModel.doubleReachCheck.value = false
                        viewModel.oneShotCheck.value = false
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.oneShotCheck.collect{
                    if(it) viewModel.rinshanCheck.value = false
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.rinshanCheck.collect{
                    if(it) viewModel.oneShotCheck.value = false
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.resultHandPoint.collect{
                    if(it == null) return@collect

                    val action = HandPointFragmentDirections.actionHandPointFragmentToPointResultFragment(resultHandPoint = it)

                    val nacController = findNavController()
                    nacController.navigate(action)

                    viewModel.consumeResultEvent()
                }
            }
        }

        binding.pointResultButton.setOnClickListener {
            lifecycleScope.launchWhenStarted {
                viewModel.onClickResult()
            }
        }

        return binding.root
    }

    private fun showSelectedWonTile() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.wonTile.collect {
                    if (it == null) return@collect
                    binding.wonTileImage.setImageResource(viewModel.getDrawableID(it.toString()))
                }
            }
        }
    }

    private fun setOpenTiles() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.openTiles.collect {
                    openTileAdapter.clear()
                    if (it.isNotEmpty()) {
                        val itemList = it.map { tiles ->
                            val ids = tiles.hands.map { tile -> viewModel.getDrawableID(tile) }
                            when (tiles) {
                                is OpenTile.Chi -> ChiItem(
                                    ids,
                                    tiles
                                ) { viewModel.onClickSelectedOpenTile(tiles) }

                                is OpenTile.Pon -> PonItem(
                                    ids,
                                    tiles
                                ) { viewModel.onClickSelectedOpenTile(tiles) }

                                is OpenTile.Kan -> {
                                    viewModel.canRinshan.value = true

                                    if (tiles.close) ClosedKanItem(
                                        ids,
                                        tiles
                                    ) { viewModel.onClickSelectedOpenTile(tiles) }
                                    else OpenKanItem(
                                        ids,
                                        tiles
                                    ) { viewModel.onClickSelectedOpenTile(tiles) }
                                }
                            }
                        }
                        openTileAdapter.addAll(itemList)
                    }

                    viewModel.isMenzen.value = it.isEmpty() || it.all { openTile -> openTile.hand.last() == 'k' }
                    viewModel.canRinshan.value = it.any { openTile -> openTile.hand.last() == 'k' || openTile.hand.last() == 'o' }
                }
            }
        }
    }

    private fun setCloseTiles() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.closeTiles.collect {
                    closeTileAdapter.clear()
                    if (it.size() != 0) {
                        Log.d("HandPointFragment", "clicked.")
                        val itemList = it.tiles.map { tile ->
                            CloseTileItem(
                                tile,
                                viewModel.getDrawableID(tile.toString())
                            ) { viewModel.onClickSelectedCloseTile(tile) }
                        }
                        closeTileAdapter.addAll(itemList)
                    }
                }
            }
        }
    }

    private fun setToggleButtons() {
        val toggleList =
            listOf(binding.ChiToggle, binding.PonToggle, binding.AnkanToggle, binding.MinkanToggle)
        val toggleListener = { button: CompoundButton, isChecked: Boolean ->

            toggleList.filterNot { it == button }.forEach {
                it.isChecked = false
            }
            button.isChecked = isChecked

            val kind = when (button) {
                binding.ChiToggle -> HandPointViewModel.Opens.CHI
                binding.PonToggle -> HandPointViewModel.Opens.PON
                binding.AnkanToggle -> HandPointViewModel.Opens.ANKAN
                binding.MinkanToggle -> HandPointViewModel.Opens.MINKAN
                else -> null
            }
            if (!isChecked) viewModel.onSelectedOpenToggle(null)
            else viewModel.onSelectedOpenToggle(kind)
        }
        toggleList.forEach {
            it.setOnCheckedChangeListener(toggleListener)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object{
        const val TAG = "HandPointFragment"
    }
}

class OpenTileListDecoration(): RecyclerView.ItemDecoration(){
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.right = 4
    }
}