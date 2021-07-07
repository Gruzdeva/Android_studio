package com.example.app2.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.DataClasses.OrderUser
import com.example.app2.R

class AdapterOrderInfo(size: Int, orderInfoArray: ArrayList<OrderUser>): RecyclerView.Adapter<AdapterOrderInfo.myVHolder>() {
    val size = size
    val orderInfoArray = orderInfoArray


    class myVHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name = itemView.findViewById<TextView>(R.id.tv_order_info_name)
        val price = itemView.findViewById<TextView>(R.id.tv_order_info_price)

        fun bind(position: Int, orderInfoArray: ArrayList<OrderUser>) {
            name.text = orderInfoArray[position].name
            price.text = orderInfoArray[position].price.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myVHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_order_info_item, parent, false)

        return myVHolder(view)
    }

    override fun onBindViewHolder(holder: myVHolder, position: Int) {
        holder.bind(position, orderInfoArray)
    }

    override fun getItemCount(): Int {
        return size
    }
}