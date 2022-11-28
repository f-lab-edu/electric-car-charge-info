package com.example.ecarchargeinfo.info.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ecarchargeinfo.databinding.RcChargerLayoutBinding
import com.example.ecarchargeinfo.info.domain.model.Charger

class ChargerAdapter(private val chargerList: ArrayList<Charger>) :
    RecyclerView.Adapter<ChargerAdapter.ChargerViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChargerViewHolder =
        ChargerViewHolder(
            RcChargerLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ChargerViewHolder, position: Int) {
        holder.bind(chargerList[position])
        println("${chargerList.get(position)} @@@@@")
    }

    override fun getItemCount(): Int = chargerList.size

    class ChargerViewHolder(val binding: RcChargerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(currentCharger: Charger) {
            binding.charger = currentCharger
        }
    }
}
