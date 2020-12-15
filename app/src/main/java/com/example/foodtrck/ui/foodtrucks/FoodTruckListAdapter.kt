package com.example.foodtrck.ui.foodtrucks

import android.content.res.Resources
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.example.foodtrck.R
import com.example.foodtrck.data.model.FoodTruck
import com.example.foodtrck.data.model.ScheduleInfo
import com.example.foodtrck.databinding.FoodtruckListItemBinding
import com.example.foodtrck.utils.convertToMiles
import com.example.foodtrck.utils.convertToRoundedMiles
import kotlinx.android.synthetic.main.foodtruck_list_item.view.*
import timber.log.Timber
import kotlin.collections.ArrayList

class FoodTruckListAdapter(
    private var listener: FoodTruckItemListener,
    private val currentLocation: Location?)
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

        return  FoodTruckListViewHolder(binding, listener, currentLocation)
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
        private val listener: FoodTruckItemListener,
        private val currentLocation: Location?)
        : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        private lateinit var foodTruck: FoodTruck
        private val open = "open"
        private val closed = "closed"
        private val primaryColor = ContextCompat.getColor(itemBinding.root.context, R.color.red_500)
        private val secondaryColor = ContextCompat.getColor(itemBinding.root.context, R.color.blue_grey_500)


        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: FoodTruck) {
            foodTruck = item
            itemBinding.foodtruckName.text = foodTruck.name
            itemBinding.foodtruckRating.text = foodTruck.rating.toString()

            val currentScheduleInfo: ScheduleInfo? = foodTruck.getCurrentSchedule()

            if (currentScheduleInfo?.isOpen() == true) {
                itemBinding.foodtruckOpen.text = open
                itemBinding.foodtruckOpen.setTextColor(primaryColor)
                itemBinding.foodtruckMilesAway.visibility = View.VISIBLE
                val miles = distanceAwayFrom(currentScheduleInfo.getLocation())

                if(miles != -1f) {
                    val distance =  "Miles: $miles"
                    //Timber.d("Foodtruck name:${foodTruck.name}, distance:$distance, open:${currentScheduleInfo.isOpen()}")
                    itemBinding.foodtruckMilesAway.text = distance
                    itemBinding.foodtruckMilesAway.setTextColor(primaryColor)
                }

            } else {
                itemBinding.foodtruckOpen.text = closed
                itemBinding.foodtruckOpen.setTextColor(secondaryColor)
                itemBinding.foodtruckMilesAway.visibility = View.INVISIBLE
            }

            if(item.images?.header?.first() != null || item.images?.logo_small != null) {
                itemBinding.foodtruckImage.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                itemBinding.foodtruckImage.scaleType = ImageView.ScaleType.FIT_CENTER
            }

            Glide.with(itemBinding.root)
                .load(item.images?.header?.first() ?: item.images?.logo_small ?: R.drawable.ic_foodtruck_placeholder)
                .into(itemBinding.foodtruckImage)
        }

        private fun distanceAwayFrom(location: Location): Float {
            if(currentLocation == null) {
                return -1f
            }

            var distance = currentLocation.distanceTo(location)
            distance = location.convertToRoundedMiles(distance)
            return distance
        }

        override fun onClick(v: View?) {
            listener.onClickFoodTruck(foodTruck.id)
        }
    }
}