package com.example.foodtrck.ui.regions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodtrck.databinding.RegionListBinding
import com.example.foodtrck.utils.Resource
import com.example.foodtrck.utils.autoCleared
import com.example.foodtrck.viewmodel.RegionListViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegionListFragment : Fragment() {

    private var binding: RegionListBinding by autoCleared()
    private val viewModel: RegionListViewModel by viewModels()
    private lateinit var adapter: RegionListAdapter
    private var regionItemListener: RegionListAdapter.RegionItemListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        regionItemListener = context as RegionListAdapter.RegionItemListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  RegionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleViewer()
        subscribeUI()
    }

    private fun setupRecycleViewer() {
        adapter = RegionListAdapter(regionItemListener!!)
        binding.regionRv.layoutManager = LinearLayoutManager(requireContext())
        binding.regionRv.adapter = adapter
    }

    private fun subscribeUI() {
        viewModel.regionList.observe(viewLifecycleOwner, { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    result.data?.let { list ->
                        adapter.updateData(list)
                    }
                    binding.progressBar.visibility = View.GONE
                }
                Resource.Status.ERROR ->
                    Timber.d(result.message)

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
            }
        })
    }
}