package com.example.foodtrck.ui.foodtrucks

import androidx.recyclerview.widget.DiffUtil
import com.example.foodtrck.data.model.FoodTruck

class FoodTruckListDiffCallback(
    private val old: List<FoodTruck>,
    private val new: List<FoodTruck>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = old.size

    override fun getNewListSize(): Int = new.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition].id == new[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return old[oldItemPosition] == new[newItemPosition]
    }
}
