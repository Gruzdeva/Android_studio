package com.example.app2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.DataClasses.MenuData
import com.example.app2.R
import com.example.app2.Tables.TableUserOrder

class AdapterRecycler(size: Int, menuArray: ArrayList<MenuData>): RecyclerView.Adapter<AdapterRecycler.myVHolder>() {
    val size = size
    val menuArray = menuArray

    class myVHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val context = itemView.context

        val nameView: TextView = itemView.findViewById(R.id.name_recycler)
        val descriptionView: TextView = itemView.findViewById(R.id.description_menu)
        val priceView: TextView = itemView.findViewById(R.id.price_recycler)
        val addBtn: Button = itemView.findViewById(R.id.add_btn)

        fun bind(position: Int, menuArray: ArrayList<MenuData>){

            nameView.text = menuArray[position].name
            priceView.text = menuArray[position].price.toString()
            descriptionView.text = menuArray[position].description

            addBtn.setOnClickListener{
                val tableUserOrder =
                    TableUserOrder(context)

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
        holder.bind(position, menuArray)
    }
}