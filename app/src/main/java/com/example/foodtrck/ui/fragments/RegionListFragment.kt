package com.example.foodtrck.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.foodtrck.R
import com.example.foodtrck.data.repository.StreetFoodRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import javax.inject.Inject

@AndroidEntryPoint
class RegionListFragment : Fragment() {

    @Inject lateinit var streetFoodRepository: StreetFoodRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.region_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uiScope = CoroutineScope(Dispatchers.Main)

        uiScope.launch {
            streetFoodRepository.getRegions().observe(viewLifecycleOwner, Observer {
                it.status
            })
        }
    }
}