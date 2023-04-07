package com.ishzk.android.majancalculator.ui.listitem

import android.view.View
import com.ishzk.android.majancalculator.R
import com.ishzk.android.majancalculator.databinding.ItemWaitHandBinding
import com.xwray.groupie.viewbinding.BindableItem

class WaitHandItem(private val imageID: Int): BindableItem<ItemWaitHandBinding>() {
    override fun initializeViewBinding(view: View): ItemWaitHandBinding = ItemWaitHandBinding.bind(view)

    override fun getLayout(): Int = R.layout.item_wait_hand

    override fun bind(viewBinding: ItemWaitHandBinding, position: Int) {
        viewBinding.waitHandTile.setImageResource(imageID)
    }
}