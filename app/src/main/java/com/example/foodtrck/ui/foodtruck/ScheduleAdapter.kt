package com.example.foodtrck.ui.foodtruck

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtrck.data.model.ScheduleInfo
import com.example.foodtrck.databinding.ScheduleListItemBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber
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
    ) : RecyclerView.ViewHolder(itemBinding.root), OnMapReadyCallback {

        private lateinit var schedule: ScheduleInfo
        private val mapView = itemBinding.scheduleMap
        private lateinit var map: GoogleMap
        private lateinit var location: LatLng


        init {
            with(mapView) {
                onCreate(null)

                getMapAsync(this@ScheduleViewHolder)
            }
        }

        private fun setMapLocation() {
            if(!::map.isInitialized) return
            with(map) {
                moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f))
                addMarker(MarkerOptions().position(location))
                mapType = GoogleMap.MAP_TYPE_NORMAL
                setOnMapClickListener {
                    Timber.d("CLICKED ON MAP")
                }
            }
        }

        override fun onMapReady(googleMap: GoogleMap?) {
            MapsInitializer.initialize(fragment.requireContext())
            map = googleMap ?: return
            setMapLocation()
        }

        fun bind(item: ScheduleInfo) {
            schedule = item

            val dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM)
            val timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT)

            val date = dateFormat.format(schedule.getStartDate())
            val startTime = timeFormat.format(schedule.getStartDate())
            val endTime = timeFormat.format(schedule.getEndDate())

            val startEndTime = "$startTime \u2014 $endTime"

            itemBinding.scheduleDate.text = date
            itemBinding.scheduleTime.text = startEndTime
            itemBinding.scheduleLocationName.text = schedule.display

            location = LatLng(schedule.getLocation().latitude, schedule.getLocation().longitude)
            setMapLocation()
        }

        fun clearView() {
            with(map) {
                clear()
                mapType = GoogleMap.MAP_TYPE_NONE
            }
        }
    }

}