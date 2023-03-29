package com.ishzk.android.majancalculator.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ishzk.android.majancalculator.R
import com.ishzk.android.majancalculator.databinding.FragmentWaitHandBinding
import com.ishzk.android.majancalculator.domain.OpenTile
import com.ishzk.android.majancalculator.domain.Tile
import com.ishzk.android.majancalculator.domain.TileKind
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
                viewModel.onLongClickImageButton(button.id)
            }

            // set long click listener to all image button.
            with(binding) {
                linearLayout.forEach { it.setOnLongClickListener(listener) }
                linearLayout2.forEach { it.setOnLongClickListener(listener) }
                linearLayout3.forEach { it.setOnLongClickListener(listener) }
                linearLayout4.forEach { it.setOnLongClickListener(listener) }
            }

            viewModel.openTiles.observe(viewLifecycleOwner){
                if(it == null) return@observe

                it.forEach { openTile -> Log.d(TAG, "Hand:${openTile.hand}") }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.longClicked.collect {
                if (it.isClicked) {
                    // show selection open tiles dialog.
                    val popup = PopupMenu(requireActivity(), binding.linearLayout)
                    popup.setOnMenuItemClickListener { item ->
                        val tile = it.hand ?: return@setOnMenuItemClickListener true
                        val kind = tile.kind
                        val num = tile.num

                        val openTile = when(item.itemId){
                            R.id.ankanItem -> OpenTile.Kan(kind, num, false)
                            R.id.minkanItem -> OpenTile.Kan(kind, num, true)
                            R.id.ponItem -> OpenTile.Pon(kind, num)
                            R.id.leftChiItem -> OpenTile.Chi(kind, OpenTile.Chi.sequenceNumbers(num).first() ?: return@setOnMenuItemClickListener true)
                            R.id.centerChiItem -> OpenTile.Chi(kind, OpenTile.Chi.sequenceNumbers(num).getOrNull(1) ?: return@setOnMenuItemClickListener true)
                            R.id.rightChiItem -> OpenTile.Chi(kind, OpenTile.Chi.sequenceNumbers(num).last() ?: return@setOnMenuItemClickListener true)
                            else -> return@setOnMenuItemClickListener true
                        }
                        viewModel.onSelectOpenHand(openTile)
                        return@setOnMenuItemClickListener true

                    }

                    val inflater: MenuInflater = popup.menuInflater
                    inflater.inflate(R.menu.menu_open_hand_select, popup.menu)
                    popup.show()
                }
                viewModel.longClicked.emit(LongClickedState(null, false))
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
