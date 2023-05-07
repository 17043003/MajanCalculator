package com.ishzk.android.majancalculator.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ishzk.android.majancalculator.databinding.FragmentPointResultBinding
import com.ishzk.android.majancalculator.ui.listitem.FannDetailItem
import com.ishzk.android.majancalculator.ui.listitem.FuDetailItem
import com.xwray.groupie.GroupieAdapter

class PointResultFragment : Fragment() {
    private var _binding: FragmentPointResultBinding? = null
    private val binding: FragmentPointResultBinding get() = _binding!!

    private val args: PointResultFragmentArgs by navArgs()

    private val viewModel: PointResultViewModel by viewModels()

    private val fannAdapter by lazy { GroupieAdapter() }
    private val fuAdapter by lazy { GroupieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPointResultBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = viewLifecycleOwner

        with(args.resultHandPoint){
            binding.resultMessage.text = total
            binding.FannResult.text = "$fann 翻"
            binding.FuResult.text = "$fu 符"

            val fannItems = yakuDetail.map {
                FannDetailItem(it.name, if(isMenzen) it.han_closed else it.han_open)
            }
            val fuItems = fuDetail.map {
                FuDetailItem(it.reason, it.fu)
            }
            fannAdapter.addAll(fannItems)
            fuAdapter.addAll(fuItems)
        }

        binding.FannDetailList.adapter = fannAdapter
        binding.FannDetailList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.fannExpandableTitle.setOnClickListener { binding.FannExpandable.toggle() }

        binding.FuDetailList.adapter = fuAdapter
        binding.FuDetailList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.fuExpandableTitle.setOnClickListener { binding.FuExpandable.toggle() }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}