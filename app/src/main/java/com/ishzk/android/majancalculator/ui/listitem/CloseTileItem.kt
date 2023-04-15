package com.ishzk.android.majancalculator.ui.listitem

import android.view.View
import com.ishzk.android.majancalculator.R
import com.ishzk.android.majancalculator.databinding.ItemSelectedHandBinding
import com.ishzk.android.majancalculator.domain.handpoint.Tile
import com.xwray.groupie.viewbinding.BindableItem

class CloseTileItem(private val tile: Tile, private val id: Int, private val listener: (Tile) -> Unit): BindableItem<ItemSelectedHandBinding>() {
    override fun initializeViewBinding(view: View) = ItemSelectedHandBinding.bind(view)

    override fun getLayout(): Int = R.layout.item_selected_hand

    override fun bind(viewBinding: ItemSelectedHandBinding, position: Int) {
        viewBinding.apply {
            handImage.setImageResource(id)

            root.setOnClickListener { listener(tile) }
        }
    }
}