package com.example.app2.ui.menu

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app2.DBReader.MenuTable
import com.example.app2.R
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
            var menuInfoSingleton: MenuInfoSingleton =
                MenuInfoSingleton.getInstance()

            if(!cursor.isAfterLast){
                nameView.text = cursor.getString(2)
                priceView.text = cursor.getInt(4).toString()

                menuInfoSingleton.id[i] = cursor.getInt(0).toInt()
                menuInfoSingleton.group_id[i] = cursor.getInt(1).toInt()
                menuInfoSingleton.name[i] = cursor.getString(2).toString()
                menuInfoSingleton.description[i] = cursor.getString(3).toString()
                menuInfoSingleton.price[i] = cursor.getInt(4).toInt()
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
        val view = inflater.inflate(R.layout.recycler_menu_item, parent, false)

        return myVHolder(view)
    }

    override fun getItemCount(): Int {
        return size
    }

    override fun onBindViewHolder(holder: myVHolder, position: Int) {
        holder.bind(cursor)
    }
}