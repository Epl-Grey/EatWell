package com.example.myapplication.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.SharedPreferenceManager
import com.example.myapplication.firebase.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class personalFragment : Fragment() {
    lateinit var heightTextView: TextView
    lateinit var ageTextView: TextView
    lateinit var weightTextView: TextView
    lateinit var aimTextView: TextView
    lateinit var activityTextView: TextView

    private lateinit var database: DatabaseReference


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        val viewP = inflater.inflate(R.layout.fragment_personal, container, false)

        heightTextView = viewP.findViewById(R.id.textView23)
        ageTextView = viewP.findViewById(R.id.textView2)
        weightTextView = viewP.findViewById(R.id.ves2)
        aimTextView = viewP.findViewById(R.id.aim2)
        activityTextView = viewP.findViewById(R.id.obras2)

        database = Firebase.database.getReference("Users")
        database.keepSynced(true)

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue<UserModel>()

                heightTextView.text = user!!.height
                ageTextView.text = user.birthdate
                weightTextView.text = user.birthdate
                aimTextView.text = user.aim
                activityTextView.text = user.activity

                println("Пол: " + user.sex)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        val login = SharedPreferenceManager.getLogin(requireContext())

        database.orderByChild("login").equalTo(login).addValueEventListener(postListener)

        return viewP
    }
}