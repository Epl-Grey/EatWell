package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import com.example.myapplication.firebase.UserModel

object SharedPreferenceManager {
    fun saveUser(context: Activity, user: UserModel){
        val sharedPreferences = context.getPreferences(Context.MODE_PRIVATE)

        with (sharedPreferences.edit()) {
            putString("login", user.login)
            apply()
        }
    }

    fun getLogin(context: Activity): String?{
        val sharedPreferences = context.getPreferences(Context.MODE_PRIVATE)

        return sharedPreferences.getString("login", null)
    }
}