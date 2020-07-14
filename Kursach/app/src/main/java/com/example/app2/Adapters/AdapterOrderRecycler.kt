package com.example.app2.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.R
import com.example.app2.Singletons.OrderSingleton

class AdapterOrderRecycler(context: Context, size: Int): RecyclerView.Adapter<AdapterOrderRecycler.VHolder>() {
    val orderSingleton = OrderSingleton.getInstance()!!
    var size = size

    class VHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val number: TextView = itemView.findViewById(R.id.number_order)
        val cost: TextView = itemView.findViewById(R.id.cost_order)

        fun bind(singleton: OrderSingleton, position: Int){
            number.text = singleton.numbers[position].toString()
            cost.text = singleton.costs[position].toString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_order_item, parent, false)

        return VHolder(view)
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(orderSingleton, position)
    }

}