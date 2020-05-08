package com.example.app2.ui.cart

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.DBReader.UserOrderTable
import com.example.app2.R

class AdapterCartRecycler(context: Context): RecyclerView.Adapter<AdapterCartRecycler.VHolder>(){
    val context = context
    lateinit var removeBtn: Button
    val dbHelper = DBHelperUserOrder(context)
    val db = dbHelper.writableDatabase
    var cursor: Cursor
    var size: Int

    init {
        cursor = db.rawQuery("SELECT COUNT(*) FROM ${UserOrderTable.TABLE_NAME}", null)
        cursor.moveToFirst()
        size = cursor.getInt(0)

        cursor = db.rawQuery("SELECT * FROM ${UserOrderTable.TABLE_NAME}", null)
        cursor.moveToFirst()
    }

    class VHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val context = itemView.context

        val name: TextView = itemView.findViewById(R.id.cart_name)
        val price: TextView = itemView.findViewById(R.id.cart_price)


        fun bind(cursor: Cursor){
            if(!cursor.isAfterLast){
                name.text = cursor.getString(1)
                price.text = cursor.getInt(2).toString()

                cursor.moveToNext()
            } else {
                cursor.close()
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_cart_item, parent, false)
        removeBtn = view.findViewById(R.id.remove_btn)

        return VHolder(view)
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(cursor)
        removeBtn.setOnClickListener{
            val tableUserOrder = TableUserOrder(context)
            tableUserOrder.deletePosition(position + 1)

            notifyItemRemoved(position)
            notifyItemRangeChanged(position, getItemCount() - position)
        }
    }

}