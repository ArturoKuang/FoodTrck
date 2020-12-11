package com.example.foodtrck.ui.foodtruck

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtrck.data.model.ScheduleInfo
import com.example.foodtrck.databinding.ScheduleListItemBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.DateFormat


class ScheduleAdapter(private val fragment: FoodTruckFragment) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    private val schedules = ArrayList<ScheduleInfo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val itemBinding: ScheduleListItemBinding =
                ScheduleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ScheduleViewHolder(itemBinding, fragment)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.bind(schedules[position])
    }

    override fun getItemCount(): Int = schedules.size

    fun updateData(newList: List<ScheduleInfo>?) {
        if(newList == null) {
            return
        }

        schedules.clear()
        schedules.addAll(newList)
        notifyDataSetChanged()
    }

    class ScheduleViewHolder(
        private val itemBinding: ScheduleListItemBinding,
        private val fragment: FoodTruckFragment
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private lateinit var schedule: ScheduleInfo

        fun bind(item: ScheduleInfo) {
            schedule = item

            val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
            val timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT)

            val date = dateFormat.format(schedule.getStartDate())
            val startTime = timeFormat.format(schedule.getStartDate())
            val endTime = timeFormat.format(schedule.getEndDate())

            val startEndTime = "$startTime \u2014 $endTime"
            val location = LatLng(schedule.getLocation().latitude, schedule.getLocation().longitude)

            itemBinding.scheduleDate.text = date
            itemBinding.scheduleTime.text = startEndTime
            itemBinding.scheduleLocationName.text = schedule.display
            itemBinding.scheduleMap.getMapAsync { googleMap ->
                googleMap.addMarker(
                    MarkerOptions()
                        .position(location))

                val options = GoogleMapOptions()
                    .liteMode(true)

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(location))
            }
        }
    }

}