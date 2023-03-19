package com.ishzk.android.majancalculator.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ishzk.android.majancalculator.R
import com.ishzk.android.majancalculator.databinding.ItemSelectedHandBinding
import com.ishzk.android.majancalculator.ui.WaitHandViewModel

class SelectHandAdapter(private val viewModel: WaitHandViewModel): ListAdapter<String, SelectImageViewHolder>(DIFF_UTIL_ITEM_CALLBACK){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectImageViewHolder {
        val view = ItemSelectedHandBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SelectImageViewHolder(view, viewModel)
    }
    override fun onBindViewHolder(holder: SelectImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class SelectImageViewHolder(private val binding: ItemSelectedHandBinding, private val viewModel: WaitHandViewModel)
    : RecyclerView.ViewHolder(binding.root){
    fun bind(item: String){
        binding.apply {
            val drawableID = getDrawableID(item)
            handImage.setImageResource(drawableID)
        }
    }

    private fun getDrawableID(id: String): Int {
        return when(id) {
            "m1" -> R.drawable.m1
            "m2" -> R.drawable.m2
            "m3" -> R.drawable.m3
            "m4" -> R.drawable.m4
            "m5" -> R.drawable.m5
            "m6" -> R.drawable.m6
            "m7" -> R.drawable.m7
            "m8" -> R.drawable.m8
            "m9" -> R.drawable.m9

            "s1" -> R.drawable.s1
            "s2" -> R.drawable.s2
            "s3" -> R.drawable.s3
            "s4" -> R.drawable.s4
            "s5" -> R.drawable.s5
            "s6" -> R.drawable.s6
            "s7" -> R.drawable.s7
            "s8" -> R.drawable.s8
            "s9" -> R.drawable.s9

            "p1" -> R.drawable.p1
            "p2" -> R.drawable.p2
            "p3" -> R.drawable.p3
            "p4" -> R.drawable.p4
            "p5" -> R.drawable.p5
            "p6" -> R.drawable.p6
            "p7" -> R.drawable.p7
            "p8" -> R.drawable.p8
            "p9" -> R.drawable.p9

            "h1" -> R.drawable.h1
            "h2" -> R.drawable.h2
            "h3" -> R.drawable.h3
            "h4" -> R.drawable.h4
            "h5" -> R.drawable.h5
            "h6" -> R.drawable.h6
            "h7" -> R.drawable.h7
            else -> 0
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