package com.example.foodtrck.ui.foodtrucks

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.databinding.FoodtruckListBinding
import com.example.foodtrck.ui.ToolbarFragment
import com.example.foodtrck.utils.Resource
import com.example.foodtrck.utils.autoCleared
import com.example.foodtrck.viewmodel.FoodTrucksViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


const val ARG_REGION_NAME = "arg_region_name"

@AndroidEntryPoint
class FoodTruckListFragment() : ToolbarFragment() {

    private var binding: FoodtruckListBinding by autoCleared()
    private val viewModel: FoodTrucksViewModel by viewModels()
    private lateinit var adapter: FoodTruckListAdapter
    private var foodTruckItemListener: FoodTruckListAdapter.FoodTruckItemListener? = null

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
    ): View? {
        Timber.d("ARG_REGION_NAME $region")
        binding = FoodtruckListBinding.inflate(inflater, container, false)
        setToolbar("Foodtrucks", true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecycleViewer()
        subscribeUI()
    }

    private fun setUpRecycleViewer() {
        adapter = FoodTruckListAdapter(foodTruckItemListener!!)
        binding.foodTrucksRv.layoutManager = LinearLayoutManager(requireContext())
        binding.foodTrucksRv.adapter = adapter
    }

    private fun subscribeUI() {
        viewModel.foodTruckList.observe(viewLifecycleOwner, { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    result.data?.let { foodTruckResponse ->
                        val list: List<FoodTruck>? =
                            foodTruckResponse.vendors?.values?.toList()

//                        ///DEBUG
//                        val df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL)
//                        val sb = StringBuilder(" FoodTrucks Opened Today: \n")
//                        if (list != null) {
//                            for (trucks in list) {
//                                val schedule = trucks.getCurrentSchedule() ?: continue
//                                val startDate = df.format(schedule.getStartDate())
//                                val endDate = df.format(schedule.getEndDate())
//                                sb.append("Name: ${trucks.name} Open: $startDate, Close: $endDate \n")
//                            }
//                        }
//                        sb.append("-----------------------------------------\n")
//                        Timber.d(sb.toString())

                        adapter.updateData(list)
                    }
                }

                Resource.Status.ERROR ->
                    Timber.d(result.message)

                Resource.Status.LOADING ->
                    binding.progressBar.visibility = View.VISIBLE
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