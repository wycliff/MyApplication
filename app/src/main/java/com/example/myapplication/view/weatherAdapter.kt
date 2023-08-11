package com.example.myapplication.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.dataSource.network.data.response.CurrentWeather
import java.util.ArrayList
import java.util.Locale


class WeatherListAdapter(
    private val vehiclesList: List<CurrentWeather>,
    private val itemClickListener: MainActivity
) /*: RecyclerView.WeatherListAdapter.ViewHolder>() , Filterable */{

//    private lateinit var context: Context
//    private var weathersListFiltered: List<CurrentWeather>
//
//
//    init {
//        this.weatherListFiltered = vehiclesList
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        context = parent.context
//        val v = LayoutInflater.from(context).inflate(R.layout.row_weather_item, parent, false)
//
//        return RecyclerView.ViewHolder(v)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val packageItem = vehiclesListFiltered[position]
//
//
//        holder.vendorType.text = packageItem.vendorType?.displayName
//        holder.regNo.text = packageItem.registrationNumber
//        holder.vendorTypeIcon.loadSvgOrOther(packageItem.vendorType?.icon)
//        holder.bind(packageItem, itemClickListener, holder.itemView, position)
//
//        when (packageItem.isValid) {
//
//            true -> {
//                holder.isValid.text = context.resources.getString(R.string.valid)
//                holder.isValid.setBackgroundResource(R.drawable.bg_submitted_doc)
//                holder.isValid.setTextColor(
//                    ContextCompat.getColor(
//                        context,
//                        R.color.sendyTertiaryBrand
//                    )
//                )
//            }
//            false -> {
//                holder.isValid.text = context.resources.getString(R.string.in_valid)
//                holder.isValid.setBackgroundResource(R.drawable.bg_document_alert)
//                holder.isValid.setTextColor(
//                    ContextCompat.getColor(
//                        context,
//                        R.color.sendyRejected
//                    )
//                )
//
//            }
//            else -> {}
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return vehiclesListFiltered.size
//    }
//
//    interface OnItemClickListener {
//        fun onItemClick(vehicle: Vehicle?, position: Int)
//    }
//
//    inner class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
//        //val vendorType: TextView = itemView.findViewById(R.id.tv_vendor_type)
//
//
//        fun bind(
//            vehicle: Vehicle?,
//            clickListener: OnItemClickListener,
//            itemView: View,
//            position: Int
//        ) {
//            itemView.setOnClickListener {
//                clickListener.onItemClick(vehicle, position)
//            }
//        }
//    }
//
//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(charSequence: CharSequence): FilterResults {
//                val charString = charSequence.toString()
//                vehiclesListFiltered = if (charString.isEmpty()) {
//                    vehiclesList
//                } else {
//                    val filteredList = ArrayList<Vehicle>()
//                    for (row in vehiclesList) {
//                        if (row.registrationNumber?.lowercase(Locale.getDefault())?.contains(
//                                charString.lowercase(
//                                    Locale.getDefault()
//                                )
//                            ) == true
//                        ) {
//                            filteredList.add(row)
//                        }
//                    }
//
//                    filteredList
//                }
//
//                val filterResults = FilterResults()
//                filterResults.values = vehiclesListFiltered
//                return filterResults
//            }
//
//            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
//                vehiclesListFiltered = filterResults.values as MutableList<Vehicle>
//                notifyDataSetChanged()
//            }
//        }
//    }


}