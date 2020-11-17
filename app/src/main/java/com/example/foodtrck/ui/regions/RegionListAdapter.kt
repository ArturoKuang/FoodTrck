package com.example.foodtrck.ui.regions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foodtrck.data.model.Region
import com.example.foodtrck.databinding.RegionListItemBinding

class RegionListAdapter(private var listener: RegionItemListener)
    : RecyclerView.Adapter<RegionListAdapter.RegionViewHolder>() {

    interface RegionItemListener {
        fun onClickRegion(regionName: String)
    }

    private val regions = ArrayList<Region>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        val binding: RegionListItemBinding =
            RegionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RegionViewHolder(binding, listener)
    }

    fun updateData(newList: List<Region>) {
        regions.clear()
        val sortedList = newList.sortedBy { region -> region.name }
        regions.addAll(sortedList)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = regions.size

    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        holder.bind(regions[position])
    }

    class RegionViewHolder(
        private val itemBinding: RegionListItemBinding,
        private val listener: RegionItemListener)
        : RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        private lateinit var region: Region

        init {
            itemBinding.root.setOnClickListener(this)
        }

        fun bind(item: Region) {
            this.region = item
            if(item.nameLong.isNullOrBlank()) {
                itemBinding.titleRegion.text = item.name
            } else {
                itemBinding.titleRegion.text = item.nameLong
            }
        }

        override fun onClick(p0: View?) {
            listener.onClickRegion(region.id)
        }
    }
}