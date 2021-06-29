package com.igld279.openweather.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.igld279.openweather.R
import com.igld279.openweather.databinding.RecyclerHeadBinding
import com.igld279.openweather.databinding.RecyclerHourlyBinding
import com.igld279.openweather.domain.entity.WeatherHourly

class AdapterHourly(private var weatherHourly: WeatherHourly)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val HEAD: Int = 1
    }

    class ViewHolderHead(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bindingHead: RecyclerHeadBinding = DataBindingUtil.bind<ViewDataBinding>(itemView)
                as RecyclerHeadBinding
    }

    class ViewHolderHourly(itemView: View) : RecyclerView.ViewHolder(itemView){
        val bindingHourly: RecyclerHourlyBinding = DataBindingUtil.bind<ViewDataBinding>(itemView)
                as RecyclerHourlyBinding

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.recycler_head -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView: RecyclerHeadBinding = RecyclerHeadBinding.inflate(
                        inflater,
                        parent,
                        false)
                ViewHolderHead(itemView.root)
            }
            R.layout.recycler_hourly -> {
                val inflater = LayoutInflater.from(parent.context)
                val itemView: RecyclerHourlyBinding = RecyclerHourlyBinding.inflate(
                        inflater,
                        parent,
                        false)
                ViewHolderHourly(itemView.root)
            }
            else -> throw IllegalStateException("viewType error")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (holder) {
            is ViewHolderHead -> {
                holder.bindingHead.textViewWindSpeed.text = weatherHourly
                        .hoursWeatherList[0].windSpeed
                holder.bindingHead.textViewPressure.text = weatherHourly
                        .hoursWeatherList[0].pressure
                holder.bindingHead.textViewHumidity.text = weatherHourly
                        .hoursWeatherList[0].humidity
                }
            is ViewHolderHourly -> {
                holder.bindingHourly.textViewRes2Date.text = weatherHourly
                        .hoursWeatherList[position - HEAD].hour
                holder.bindingHourly.imageViewRes2Clouds.setImageResource(
                        weatherHourly.hoursWeatherList[position - HEAD].icon)
                holder.bindingHourly.textViewRes2Temp.text = weatherHourly
                        .hoursWeatherList[position - HEAD].temp
                }
            else -> throw IllegalStateException("holder error")
        }
    }


    override fun getItemCount(): Int = weatherHourly.hoursWeatherList.size.plus(1)

    override fun getItemViewType(position: Int): Int{
        return if (position == 0) {
            R.layout.recycler_head
        } else {
            R.layout.recycler_hourly
        }
    }
}