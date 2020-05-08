package com.example.app2.ui.menu

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.R
import com.example.app2.ui.cart.TableUserOrder


class AdapterRecycler(context: Context, size: Int): RecyclerView.Adapter<AdapterRecycler.myVHolder>() {
    val menuSingleton = MenuSingleton.getInstance()!!
    val size = size

    class myVHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val context = itemView.context

        val nameView: TextView = itemView.findViewById(R.id.name_recycler)
        val descriptionView: TextView = itemView.findViewById(R.id.description_menu)
        val priceView: TextView = itemView.findViewById(R.id.price_recycler)
        val addBtn: Button = itemView.findViewById(R.id.add_btn)

        fun bind(position: Int, singleton: MenuSingleton){
            nameView.text = singleton.name[position]
            priceView.text = singleton.price[position].toString()
            descriptionView.text = singleton.description[position]

            addBtn.setOnClickListener{
                val tableUserOrder = TableUserOrder(context)

                val name = nameView.text.toString()
                val price = priceView.text.toString().toInt()

                tableUserOrder.add_position(name, price)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myVHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_menu_item, parent, false)

        return myVHolder(view)
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: myVHolder, position: Int) {
        holder.bind(position, menuSingleton)
    }
}