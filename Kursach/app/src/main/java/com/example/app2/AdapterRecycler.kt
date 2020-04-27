package com.example.app2

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.DBReader.MenuTable

class AdapterRecycler(context: Context): RecyclerView.Adapter<AdapterRecycler.myVHolder>() {
    val dbHelper = DBHelperMenu(context)
    val db = dbHelper.writableDatabase
    var cursor = db.query(MenuTable.TABLE_NAME, null, null, null, null, null, null)

    val indexId = cursor.getColumnIndex(MenuTable.COLUMN_ID)
    val indexGroupId = cursor.getColumnIndex(MenuTable.COLUMN_GROUP_ID)
    val indexName = cursor.getColumnIndex(MenuTable.COLUMN_NAME)
    val indexDescription = cursor.getColumnIndex(MenuTable.COLUMN_DESCRIPTION)
    val indexPrice = cursor.getColumnIndex(MenuTable.COLUMN_PRICE)

    var size: Int

    init {
        cursor = db.rawQuery("SELECT COUNT(*) FROM ${MenuTable.TABLE_NAME}", null)
        cursor.moveToFirst()

        size = cursor.getInt(0)

        cursor = db.rawQuery("SELECT * FROM ${MenuTable.TABLE_NAME}", null)

    }

    class myVHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val context = itemView.context

        val nameView: TextView = itemView.findViewById(R.id.name_recycler)
        val priceView: TextView = itemView.findViewById(R.id.price_recycler)
        val imageView: ImageView = itemView.findViewById(R.id.picture_recycler)

        init {
            super.itemView
            itemView.setOnClickListener(View.OnClickListener {

                val intent = Intent(context, ActivityMenu::class.java)
                intent.putExtra("position", adapterPosition)
                context.startActivity(intent)
            })
        }

        fun bind(cursor: Cursor){
            if(!cursor.isAfterLast){
                nameView.text = cursor.getString(2)
                priceView.text = cursor.getString(4)
            } else {
                cursor.close()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myVHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_item, parent, false)

        return myVHolder(view)
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: myVHolder, position: Int) {
        holder.bind(cursor)
    }
}