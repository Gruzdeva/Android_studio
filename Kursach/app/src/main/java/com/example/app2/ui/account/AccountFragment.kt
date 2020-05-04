package com.example.app2.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.app2.R
import com.example.app2.TableUsers
import com.example.app2.UserProfile

class AccountFragment: Fragment() {

    private lateinit var accountViewModel: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        accountViewModel =
            ViewModelProviders.of(this).get(AccountViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_account, container, false)
        val textView: TextView = root.findViewById(R.id.text_account)
        accountViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val userProfile = UserProfile.getInstance()
        val tableUsers = TableUsers(activity!!.applicationContext)

        val name: TextView = root.findViewById(R.id.name_of_account)
        val points: TextView = root.findViewById(R.id.points_acc)

        name.text = userProfile.name
        points.text = "Points: ${userProfile.points.toString()}"
        tableUsers.updatePoints(userProfile.points)
        return root
    }
}