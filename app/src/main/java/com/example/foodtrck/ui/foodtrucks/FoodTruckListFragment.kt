package com.example.foodtrck.ui.foodtrucks

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodtrck.R
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.databinding.FoodtruckListBinding
import com.example.foodtrck.ui.SpaceItemDecoration
import com.example.foodtrck.ui.ToolbarFragment
import com.example.foodtrck.utils.GpsTracker
import com.example.foodtrck.utils.Resource
import com.example.foodtrck.utils.autoCleared
import com.example.foodtrck.viewmodel.ARG_REGION_NAME
import com.example.foodtrck.viewmodel.FoodTrucksViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import timber.log.Timber

@AndroidEntryPoint
class FoodTruckListFragment() : ToolbarFragment() {

    private var binding: FoodtruckListBinding by autoCleared()
    private val viewModel: FoodTrucksViewModel by viewModels()
    private lateinit var gpsTracker: GpsTracker
    private lateinit var adapter: FoodTruckListAdapter
    private var foodTruckItemListener: FoodTruckListAdapter.FoodTruckItemListener? = null
    private var foodtruckList: List<FoodTruck> = emptyList()
    private val currentLocation: Location? by lazy {
        gpsTracker = GpsTracker(requireContext())
        gpsTracker.getCurrentLocation()
    }

    val region: String by lazy {
        arguments?.getString(ARG_REGION_NAME, "") ?: ""
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        foodTruckItemListener = context as FoodTruckListAdapter.FoodTruckItemListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FoodtruckListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycleViewer()
        subscribeUI()
        val regionName = region.capitalize(Locale.ROOT)
        setToolbar(regionName, true)

        binding.chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chip_distance -> {
                    if (currentLocation == null) {
                        return@setOnCheckedChangeListener
                    }

                    val distanceSortedList = foodtruckList.sortedBy { foodTruck ->
                        val distance = foodTruck.distanceAwayFrom(currentLocation!!)
                        if (distance == -1f) {
                            return@sortedBy Float.MAX_VALUE
                        }
                        return@sortedBy distance
                    }
                    updateList(distanceSortedList)
                }
                R.id.chip_rating -> {
                    val ratingSortedList = foodtruckList.sortedByDescending { foodTruck ->
                        foodTruck.rating
                    }
                    updateList(ratingSortedList)
                }
                R.id.chip_name -> {
                    val nameSortedList = foodtruckList.sortedBy { foodTruck ->
                        foodTruck.name
                    }
                    updateList(nameSortedList)
                }
            }
        }
    }

    private fun updateList(list: List<FoodTruck>) {
        adapter.updateData(list)
        binding.foodTrucksRv.scrollToPosition(0)
    }

    private fun setUpRecycleViewer() {
        adapter = FoodTruckListAdapter(foodTruckItemListener!!, currentLocation)
        val bottomTopSpacing = resources.getDimensionPixelSize(R.dimen.keyline_3)
        val leftRightSpacing = resources.getDimensionPixelSize(R.dimen.keyline_5)

        val itemDecoration =
            SpaceItemDecoration(
                leftRightSpacing,
                bottomTopSpacing,
                leftRightSpacing,
                bottomTopSpacing
            )

        binding.foodTrucksRv.layoutManager = LinearLayoutManager(requireContext())
        binding.foodTrucksRv.adapter = adapter
        binding.foodTrucksRv.addItemDecoration(itemDecoration)
    }

    private fun subscribeUI() {
        viewModel.foodTruckList.observe(
            viewLifecycleOwner,
            { result ->
                when (result.status) {
                    Resource.Status.SUCCESS -> {
                        result.data?.let { foodTruckResponse ->
                            val list: List<FoodTruck>? =
                                foodTruckResponse.vendors?.values?.toList()

                            if (list != null) {
                                foodtruckList = list
                            }
                            adapter.updateData(list)
                        }
                    }

                    Resource.Status.ERROR ->
                        Timber.d(result.message)

                    Resource.Status.LOADING ->
                        binding.progressBar.visibility = View.VISIBLE
                }
            }
        )
    }

    companion object {
        const val TAG = "FOODTRUCKLIST_FRAGMENT"

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
