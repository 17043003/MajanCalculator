package com.ishzk.android.majancalculator.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ishzk.android.majancalculator.R
import com.ishzk.android.majancalculator.databinding.ItemSelectedHandBinding
import com.ishzk.android.majancalculator.domain.CloseTiles
import com.ishzk.android.majancalculator.domain.HandTiles
import com.ishzk.android.majancalculator.ui.WaitHandViewModel

class SelectHandAdapter(private val viewModel: WaitHandViewModel): ListAdapter<String, SelectImageViewHolder>(DIFF_UTIL_ITEM_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectImageViewHolder {
        val view = ItemSelectedHandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectImageViewHolder(view, viewModel)
    }
    override fun onBindViewHolder(holder: SelectImageViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.setOnClickListener(getItem(position))
    }
}

class SelectImageViewHolder(private val binding: ItemSelectedHandBinding, private val viewModel: WaitHandViewModel)
    : RecyclerView.ViewHolder(binding.root){
    fun bind(item: String){
        binding.apply {
            val drawableID = viewModel.getDrawableID(item)
            handImage.setImageResource(drawableID)
        }
    }

    fun setOnClickListener(item: String){
        binding.handImage.setOnClickListener {
            val closeTiles = viewModel.handTiles.value?.closeTiles?.splitByKindWithPrefix()?.toMutableList()
            closeTiles?.remove(item)

            val c = CloseTiles("")
            closeTiles?.forEach {
                c.add(it)
            }

            val open = viewModel.handTiles.value?.openTiles?.toMutableList() ?: mutableListOf()
            viewModel.handTiles.postValue(HandTiles(c, open))
        }
    }
}

val DIFF_UTIL_ITEM_CALLBACK = object : DiffUtil.ItemCallback<String>() {
    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}