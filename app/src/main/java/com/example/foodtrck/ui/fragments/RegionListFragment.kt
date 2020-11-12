package com.example.foodtrck.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
class RegionListFragment : Fragment(), RegionListAdapter.RegionItemListener {

    private var binding: RegionListBinding by autoCleared()
    private val viewModel: RegionListViewModel by viewModels()
    private lateinit var adapter: RegionListAdapter


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
        adapter = RegionListAdapter(this)
        binding.regionRv.layoutManager = LinearLayoutManager(requireContext())
        binding.regionRv.adapter = adapter
    }

    private fun subscribeUI() {
        viewModel.regionList.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    result.data?.results?.let { list ->
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

    override fun onClickRegion(regionName: String) {
        TODO("Not yet implemented")
    }
}