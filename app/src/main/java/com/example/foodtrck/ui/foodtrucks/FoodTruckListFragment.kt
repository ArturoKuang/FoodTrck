package com.example.foodtrck.ui.foodtrucks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.foodtrck.databinding.FoodtruckListBinding
import com.example.foodtrck.utils.autoCleared

class FoodTruckListFragment: Fragment() {

    private var binding: FoodtruckListBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FoodtruckListBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): FoodTruckListFragment {
            return FoodTruckListFragment()
        }
    }
}