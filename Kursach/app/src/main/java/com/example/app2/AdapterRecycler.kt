package com.example.app2

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.DBReader.MenuTable
import java.io.IOException

class AdapterRecycler(context: Context): RecyclerView.Adapter<AdapterRecycler.myVHolder>() {
    var dbHelper = DBHelperMenu(context)
    var db: SQLiteDatabase
    var cursor: Cursor

    var size: Int

    init {
        try {
            dbHelper.updateDataBase()
        } catch (mIOException: IOException) {
            throw Error("UnableToUpdateDatabase")
        }

        try {
            db = dbHelper.writableDatabase
        } catch (mSQLException: SQLException) {
            throw mSQLException
        }

        cursor = db.rawQuery("SELECT COUNT(*) FROM ${MenuTable.TABLE_NAME}", null)
        cursor.moveToFirst()

        size = cursor.getInt(0)
        Log.d("PROVERKA", size.toString())

        cursor = db.rawQuery("SELECT * FROM ${MenuTable.TABLE_NAME}", null)
        cursor.moveToFirst()

    }

    class myVHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val context = itemView.context

        val nameView: TextView = itemView.findViewById(R.id.name_recycler)
        val priceView: TextView = itemView.findViewById(R.id.price_recycler)
//        val imageView: ImageView = itemView.findViewById(R.id.picture_recycler)

        init {
            super.itemView
            itemView.setOnClickListener(View.OnClickListener {

                val intent = Intent(context, ActivityPagerMenu::class.java)
                intent.putExtra("position", adapterPosition)
                context.startActivity(intent)
            })
        }

        fun bind(cursor: Cursor){
            var i = 0
            var menuInfoSingleton = MenuInfoSingleton.getInstance()

            if(!cursor.isAfterLast){
                nameView.text = cursor.getString(2)
                priceView.text = cursor.getInt(4).toString()

                menuInfoSingleton.id[i] = cursor.getInt(0)
                menuInfoSingleton.group_id[i] = cursor.getInt(1)
                menuInfoSingleton.name[i] = cursor.getString(2)
                menuInfoSingleton.description[i] = cursor.getString(3)
                menuInfoSingleton.price[i] = cursor.getInt(4)
                i++

                cursor.moveToNext()
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