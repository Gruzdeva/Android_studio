package com.example.app2.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.Activities.ActivityOrderInfo
import com.example.app2.R
import com.example.app2.Singletons.OrderSingleton

class AdapterOrderRecycler(size: Int): RecyclerView.Adapter<AdapterOrderRecycler.VHolder>() {
    var size = size

    class VHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val context = itemView.context
        val number: TextView = itemView.findViewById(R.id.date_order)
        val cost: TextView = itemView.findViewById(R.id.cost_order)
        val orderSingleton = OrderSingleton.getInstance()!!

        init {
            super.itemView
            itemView.setOnClickListener(View.OnClickListener {

                val intent = Intent(context, ActivityOrderInfo::class.java)
                intent.putExtra("key", orderSingleton.id[adapterPosition])
                context.startActivity(intent)
            })
        }

        fun bind(position: Int){
            number.text = orderSingleton.dates[position].toString()
            cost.text = orderSingleton.costs[position].toString()
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
        holder.bind(position)
    }

}