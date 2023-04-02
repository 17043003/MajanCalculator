package com.ishzk.android.majancalculator.ui.listitem

import android.view.View
import com.ishzk.android.majancalculator.R
import com.ishzk.android.majancalculator.databinding.ItemClosedKanBinding
import com.ishzk.android.majancalculator.databinding.ItemOpenChiBinding
import com.ishzk.android.majancalculator.databinding.ItemOpenKanBinding
import com.ishzk.android.majancalculator.databinding.ItemOpenPonBinding
import com.ishzk.android.majancalculator.domain.OpenTile
import com.xwray.groupie.viewbinding.BindableItem

class PonItem(private val ids: List<Int>, private val item: OpenTile, private val listener: (OpenTile) -> Unit ): BindableItem<ItemOpenPonBinding>() {
    override fun initializeViewBinding(view: View): ItemOpenPonBinding = ItemOpenPonBinding.bind(view)

    override fun getLayout(): Int = R.layout.item_open_pon

    override fun bind(viewBinding: ItemOpenPonBinding, position: Int) {
        viewBinding.apply {
            if(ids.size != 3) return@apply
            rotatedImage.setImageResource(ids[0])
            centerImage.setImageResource(ids[1])
            rightImage.setImageResource(ids[2])
        }

        viewBinding.root.setOnClickListener{
            listener(item)
        }
    }
}

class ChiItem(private val ids: List<Int>, private val item: OpenTile, private val listener: (OpenTile) -> Unit ): BindableItem<ItemOpenChiBinding>() {
    override fun initializeViewBinding(view: View): ItemOpenChiBinding = ItemOpenChiBinding.bind(view)

    override fun getLayout(): Int = R.layout.item_open_chi

    override fun bind(viewBinding: ItemOpenChiBinding, position: Int) {
        viewBinding.apply {
            if(ids.size != 3) return@apply
            rotatedChi.setImageResource(ids[0])
            centerChi.setImageResource(ids[1])
            rightChi.setImageResource(ids[2])
        }

        viewBinding.root.setOnClickListener{
            listener(item)
        }
    }
}

class OpenKanItem(private val ids: List<Int>, private val item: OpenTile, private val listener: (OpenTile) -> Unit ): BindableItem<ItemOpenKanBinding>() {
    override fun initializeViewBinding(view: View): ItemOpenKanBinding = ItemOpenKanBinding.bind(view)

    override fun getLayout(): Int = R.layout.item_open_kan

    override fun bind(viewBinding: ItemOpenKanBinding, position: Int) {
        viewBinding.apply {
            if(ids.size != 4) return@apply
            rotatedKan.setImageResource(ids[0])
            centerKan1.setImageResource(ids[1])
            centerKan2.setImageResource(ids[2])
            rightKan.setImageResource(ids[3])
        }

        viewBinding.root.setOnClickListener{
            listener(item)
        }
    }
}

class ClosedKanItem(private val ids: List<Int>, private val item: OpenTile, private val listener: (OpenTile) -> Unit): BindableItem<ItemClosedKanBinding>() {
    override fun initializeViewBinding(view: View): ItemClosedKanBinding = ItemClosedKanBinding.bind(view)

    override fun getLayout(): Int = R.layout.item_closed_kan

    override fun bind(viewBinding: ItemClosedKanBinding, position: Int) {
        viewBinding.apply {
            if(ids.size != 4) return@apply
            backKan1.setImageResource(ids[0])
            centerKan1.setImageResource(ids[1])
            centerKan2.setImageResource(ids[2])
            backKan2.setImageResource(ids[3])
        }

        viewBinding.root.setOnClickListener{
            listener(item)
        }
    }
}