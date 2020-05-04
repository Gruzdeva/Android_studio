package com.example.app2.ui.order

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.R
import com.example.app2.UserProfile
import com.example.app2.DBReader.OrdersTable

class AdapterOrderRecycler(context: Context): RecyclerView.Adapter<AdapterOrderRecycler.VHolder>() {
    var dbHelper = DBHelperAllOrders(context)
    var db = dbHelper.writableDatabase
    var cursor: Cursor
    var size: Int
    val userProfile = UserProfile.getInstance()

    init {
        cursor = db.rawQuery("SELECT COUNT(*) FROM ${OrdersTable.TABLE_NAME} " +
                "WHERE ${OrdersTable.COLUMN_USER_ID} = ?", arrayOf(userProfile.id.toString()))
        cursor.moveToFirst()
        size = cursor.getInt(0)
        Log.d("PROVERKAR", size.toString())
        cursor = db.rawQuery("SELECT * FROM ${OrdersTable.TABLE_NAME} " +
                "WHERE ${OrdersTable.COLUMN_USER_ID} = ?", arrayOf(userProfile.id.toString()))
        cursor.moveToFirst()
    }

    class VHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val number: TextView = itemView.findViewById(R.id.number_order)
        val cost: TextView = itemView.findViewById(R.id.cost_order)

        fun bind(cursor: Cursor){
            if(!cursor.isAfterLast){
                number.text = cursor.getString(2)
                cost.text = cursor.getInt(3).toString()

                cursor.moveToNext()
            } else {
                cursor.close()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterOrderRecycler.VHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_order_item, parent, false)

        return VHolder(view)
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: AdapterOrderRecycler.VHolder, position: Int) {
        holder.bind(cursor)
    }

}