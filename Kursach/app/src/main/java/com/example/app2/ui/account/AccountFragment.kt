package com.example.app2.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.app2.R
import com.example.app2.TableUsers

class AccountFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_account, container, false)

        val tableUsers = TableUsers(activity!!.applicationContext)
        var userProfile = tableUsers.loadRemember()

        val name: TextView = root.findViewById(R.id.name_of_account)
        val points: TextView = root.findViewById(R.id.points_acc)

        name.text = userProfile.name
        points.text = "Points: ${userProfile.points}"

        return root
    }
}