package com.example.myapplication

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.getString
import com.example.myapplication.firebase.UserModel

object SharedPreferenceManager {
    fun saveUser(context: Context, user: UserModel){
        val sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("login", user.login)
        editor.apply()
    }

    fun getLogin(context: Context): String?{
        val sharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

        return sharedPreferences.getString("login", null)
    }
}