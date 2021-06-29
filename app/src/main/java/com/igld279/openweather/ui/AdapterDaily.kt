package com.igld279.openweather.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.igld279.openweather.databinding.RecyclerDailyBinding
import com.igld279.openweather.domain.entity.WeatherDaily

class AdapterDaily(private  val weatherDaily: WeatherDaily)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val binding: RecyclerDailyBinding = DataBindingUtil.bind<ViewDataBinding>(itemView)
                as RecyclerDailyBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView: RecyclerDailyBinding = RecyclerDailyBinding.inflate(
                inflater,
                parent,
                false)
        return ViewHolder(itemView.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (holder) {
            is ViewHolder -> {
                holder.binding.textViewDayMonth.text = weatherDaily
                        .dailyWeatherList[position].data
                holder.binding.textViewDayWeek.text = weatherDaily
                        .dailyWeatherList[position].dayWeek

                holder.binding.imageViewClouds.setImageResource(
                        weatherDaily.dailyWeatherList[position].icon)
                holder.binding.textViewTempOnDay.text = weatherDaily
                        .dailyWeatherList[position].tempDay
                holder.binding.textViewTempOnNight.text = weatherDaily
                        .dailyWeatherList[position].tempNight
            }
            else -> throw IllegalStateException("holder error")
        }
    }

    override fun getItemCount(): Int {
        return weatherDaily.weatherOneCallResponse.daily.size
    }
}