package com.ishzk.android.majancalculator.ui.listitem

import android.view.View
import com.ishzk.android.majancalculator.R
import com.ishzk.android.majancalculator.databinding.ItemFannfuDetailBinding
import com.xwray.groupie.viewbinding.BindableItem

abstract class FannFuDetailItem: BindableItem<ItemFannfuDetailBinding>() {
    override fun initializeViewBinding(view: View): ItemFannfuDetailBinding = ItemFannfuDetailBinding.bind(view)

    override fun getLayout(): Int = R.layout.item_fannfu_detail
}

class FannDetailItem(private val fannReason: String, private val fann: Int): FannFuDetailItem(){
    override fun bind(viewBinding: ItemFannfuDetailBinding, position: Int) {
        viewBinding.apply {
            reason.text = fannReason
            point.text = "${fann}翻"
        }
    }
}

class FuDetailItem(private val fuReason: String, private val fu: Int): FannFuDetailItem(){
    override fun bind(viewBinding: ItemFannfuDetailBinding, position: Int) {
        viewBinding.apply {
            reason.text = fuReason
            point.text = "${fu}符"
        }
    }
}