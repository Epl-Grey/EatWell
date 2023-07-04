package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.example.myapplication.DatabaseHelper
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class DishManager(val context: Context) {
    private var dbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Pills")
    var pills: ArrayList<DishData> = arrayListOf<DishData>()

    var listener: (pills: ArrayList<DishData>) -> Unit = {}

    init {
        val pillListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                pills.clear()
                if(snapshot.exists()){
                    for (userSnap in snapshot.children){
                        val userData = userSnap.getValue<DishData>()
                        pills.add(userData!!)
                    }
                }

                System.out.println(pills)
                val dbHelper: DatabaseHelper = DatabaseHelper(context)
                val sharedPreference = context.getSharedPreferences("UserInfo",Context.MODE_PRIVATE)
                val userName = sharedPreference.getString("userName", "userId don't set")

                println("userName: $userName")
                dbHelper.cleanDB()
                pills.forEach {
                    if(it.userId == userName) {
                        dbHelper.insertPill(it)
                    }
                }
                listener(pills)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(ContentValues.TAG, "loadPill:onCancelled", databaseError.toException())
            }
        }
        dbRef.addValueEventListener(pillListener)
    }

    fun saveData(userId: String,
                 dishName: String,
                 dishData: String,
                 dishProteins: String,
                 dishFats: String,
                 dishCarbohydrates: String,
                 dishIngredients: String,
                 dishRecipe: String,
                 dishTime: String,
                 dishTimeDay: String){
        val dishId = dbRef.push().key!!

        val employeeModel = DishData(dishId, userId, dishName, dishData, dishProteins, dishFats, dishCarbohydrates, dishIngredients, dishRecipe, dishTime, dishTimeDay)

        dbRef.child(dishId).setValue(employeeModel)
    }
}