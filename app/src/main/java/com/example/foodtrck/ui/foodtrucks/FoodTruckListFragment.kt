package com.example.foodtrck.ui.foodtrucks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.foodtrck.databinding.FoodtruckListBinding
import com.example.foodtrck.utils.Resource
import com.example.foodtrck.utils.autoCleared
import com.example.foodtrck.viewmodel.FoodTrucksViewModel
import com.example.foodtrck.viewmodel.RegionListViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

private const val ARG_REGION_NAME = "region_name"

@AndroidEntryPoint
class FoodTruckListFragment(): Fragment() {

    private var binding: FoodtruckListBinding by autoCleared()
    private val viewModel: FoodTrucksViewModel by viewModels()
    val region: String by lazy {
        arguments?.getString(ARG_REGION_NAME, "") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("ARG_REGION_NAME $region")
        binding = FoodtruckListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //println(viewModel.regionList)
//        foodTrucksViewModel.regionName.observe(viewLifecycleOwner, {
//                Timber.d(it)
//            }
//        )

//        foodTrucksViewModel.foodTruckList.observe(viewLifecycleOwner, { result ->
//            when(result.status) {
//                Resource.Status.SUCCESS -> {
//                    Timber.d("FoodTrucks: $result?.data")
//                }
//            }
//        })

        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.foodTruckList.observe(viewLifecycleOwner, { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    result.data?.let { list ->
                        Timber.d("$list")
                    }
                }
                Resource.Status.ERROR ->
                    Timber.d(result.message)
            }
        })
    }

    companion object {
        fun newInstance(region: String): FoodTruckListFragment {
            val args = Bundle().apply {
                putSerializable(ARG_REGION_NAME, region)
            }

            return FoodTruckListFragment().apply {
                arguments = args
            }
        }
    }
}