package com.example.myapplication.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.dataSource.network.data.response.CurrentWeather
import com.example.myapplication.utils.Constants.Companion.CLOUDY
import com.example.myapplication.utils.Constants.Companion.RAINY
import com.example.myapplication.utils.Constants.Companion.SUNNY
import com.example.myapplication.utils.DateTimeUtils.Companion.formatStringToDate
import com.example.myapplication.utils.DateTimeUtils.Companion.getDayOfWeek


class WeatherListAdapter(
    private val weatherList: List<CurrentWeather>
) : RecyclerView.Adapter<WeatherListAdapter.ViewHolder>() {

    private lateinit var context: Context
//    private var weathersListFiltered: List<CurrentWeather>
//    init {
//        this.weatherListFiltered = vehiclesList
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val v = LayoutInflater.from(context).inflate(R.layout.row_weather_item, parent, false)

        return ViewHolder(v)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weatherItem = weatherList[position]

        holder.tvWeekDay.text = getDayOfWeek(formatStringToDate(weatherItem.date))
        holder.tvTemp.text =
            String.format(
                context.getString(R.string.text_degrees),
                weatherItem.main?.temp.toString()
            )

        when (weatherItem.weather?.get(0)?.main) {
            CLOUDY -> {
                holder.weatherIcon.setImageDrawable(context.getDrawable(R.drawable.ic_cloudy))
            }

            SUNNY -> {
                holder.weatherIcon.setImageDrawable(context.getDrawable(R.drawable.ic_clear))
            }

            RAINY -> {
                holder.weatherIcon.setImageDrawable(context.getDrawable(R.drawable.ic_rainy))
            }

            else -> {
                holder.weatherIcon.setImageDrawable(context.getDrawable(R.drawable.ic_clear))
            }
        }
    }


    override fun getItemCount(): Int {
        return weatherList.size
    }

    inner class ViewHolder(rootView: View) : RecyclerView.ViewHolder(rootView) {
        val tvWeekDay: TextView = itemView.findViewById(R.id.tv_week_day)
        val weatherIcon: ImageView = itemView.findViewById(R.id.iv_weather_icon)
        val tvTemp: TextView = itemView.findViewById(R.id.tv_temp)
    }

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