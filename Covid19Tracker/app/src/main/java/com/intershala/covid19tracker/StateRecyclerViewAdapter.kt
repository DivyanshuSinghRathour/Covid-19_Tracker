package com.intershala.covid19tracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StateRecyclerViewAdapter(private val stateList: List<StateModel>): RecyclerView.Adapter<StateRecyclerViewAdapter.StateRecyclerViewViewHolder>() {
    class StateRecyclerViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtStateName: TextView=itemView.findViewById(R.id.txtStateName)
        val txtCases: TextView=itemView.findViewById(R.id.txtCases)
        val txtRecovered: TextView=itemView.findViewById(R.id.txtRecovered)
        val txtDeaths: TextView=itemView.findViewById(R.id.txtDeaths)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StateRecyclerViewViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.state_recycler_view_item,parent,false)
        return StateRecyclerViewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StateRecyclerViewViewHolder, position: Int) {
        val stateData=stateList[position]
        holder.txtStateName.text=stateData.state
        holder.txtCases.text =stateData.cases.toString()
        holder.txtRecovered.text=stateData.recovered.toString()
        holder.txtDeaths.text=stateData.deaths.toString()
    }

    override fun getItemCount(): Int {

        return stateList.size
    }
}