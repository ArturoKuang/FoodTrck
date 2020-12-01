package com.example.foodtrck.ui.foodtrucks

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.example.foodtrck.R
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.databinding.FoodtruckListItemBinding
import kotlin.collections.ArrayList

class FoodTruckListAdapter(private var listener: FoodTruckItemListener)
    : RecyclerView.Adapter<FoodTruckListAdapter.FoodTruckListViewHolder>() {

    interface  FoodTruckItemListener {
        fun onClickFoodTruck(foodTruckID: String)
    }

    private val foodtrucks = ArrayList<FoodTruck>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodTruckListViewHolder {

        val binding: FoodtruckListItemBinding =
            FoodtruckListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return  FoodTruckListViewHolder(binding, listener)
    }

    fun updateData(newList: List<FoodTruck>?) {
        if(newList == null) {
            return
        }

        foodtrucks.clear()
        foodtrucks.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int =
        foodtrucks.size

    override fun onBindViewHolder(
        holder: FoodTruckListViewHolder,
        position: Int
    ) {
        holder.bind(foodtrucks[position])
    }

    class FoodTruckListViewHolder(
        private val itemBinding: FoodtruckListItemBinding,
        private val listener: FoodTruckItemListener)
        : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        private lateinit var foodTruck: FoodTruck

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: FoodTruck) {
            foodTruck = item
            itemBinding.foodtruckName.text = foodTruck.name

            val open = "open"
            val closed = "closed"
            if (foodTruck.getCurrentSchedule()?.isOpen() == true) {
                itemBinding.foodtruckOpen.text = open
            } else {
                itemBinding.foodtruckOpen.text = closed
            }

            Glide.with(itemBinding.root)
                .load(item.images?.header?.first() ?: item.images?.logo_small)
                .placeholder(R.drawable.ic_foodtruck_placeholder)
                .transform(FitCenter())
                .into(itemBinding.foodtruckImage)
        }

        override fun onClick(v: View?) {
            listener.onClickFoodTruck(foodTruck.id)
        }
    }
}