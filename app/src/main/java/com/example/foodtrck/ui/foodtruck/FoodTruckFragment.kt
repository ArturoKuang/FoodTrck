package com.example.foodtrck.ui.foodtruck

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodtrck.R
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.databinding.FoodtruckDetailsFragmentBinding
import com.example.foodtrck.ui.SpaceItemDecoration
import com.example.foodtrck.ui.ToolbarFragment
import com.example.foodtrck.utils.autoCleared
import com.example.foodtrck.viewmodel.FoodTruckViewModel
import dagger.hilt.android.AndroidEntryPoint

const val ARG_FOODTRUCK = "arg_foodtruck"

@AndroidEntryPoint
class FoodTruckFragment: ToolbarFragment() {

    private var binding: FoodtruckDetailsFragmentBinding by autoCleared()
    private val foodViewModel: FoodTruckViewModel by viewModels()
    private lateinit var scheduleAdapter: ScheduleAdapter

    private val recycleListener = RecyclerView.RecyclerListener { holder ->
        val mapHolder = holder as ScheduleAdapter.ScheduleViewHolder
        mapHolder.clearView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoodtruckDetailsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUI()
        setUpRecyclerView()
    }

    private fun subscribeUI() {
        foodViewModel.foodTruck.observe(viewLifecycleOwner, { foodTruck ->
            Glide.with(this)
                .load(foodTruck?.images?.header?.first())
                .placeholder(R.drawable.ic_foodtruck_placeholder)
                .into(binding.foodTruckDetailsImage)

            setToolbar(foodTruck?.name ?: "", true)

            binding.foodTruckDetailsPhone.text = foodTruck?.phone
            binding.foodTruckDetailsWebsite.text = foodTruck?.url
            binding.rating.text = foodTruck?.rating.toString()
            binding.foodTruckDetailsEmail.text = foodTruck?.email
            binding.foodTruckDetailsDescription.text = foodTruck?.description

            scheduleAdapter.updateData(foodTruck?.schedule)
        })
    }

    private fun setUpRecyclerView() {
        scheduleAdapter = ScheduleAdapter(this)
        val spacing = resources.getDimensionPixelSize(R.dimen.keyline_2)
        val itemDecoration = SpaceItemDecoration(spacing)

        binding.scheduleRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = scheduleAdapter
            addItemDecoration(itemDecoration)
            setRecyclerListener(recycleListener)
        }
    }

    companion object {
        const val TAG = "FOODTRUCK_FRAGMENT"

        fun newInstance(foodTruckID: String): FoodTruckFragment {
            val args = Bundle().apply {
                putSerializable(ARG_FOODTRUCK, foodTruckID)
            }

            return FoodTruckFragment().apply {
                arguments = args
            }
        }
    }
}