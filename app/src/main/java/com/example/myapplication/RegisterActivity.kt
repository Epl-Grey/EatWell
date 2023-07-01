package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.firebase.UserModel
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.security.MessageDigest

class RegisterActivity : AppCompatActivity() {
    lateinit var loginEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var repeatPasswordEditText: EditText
    lateinit var registerButton: Button
    lateinit var loginButton: Button

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        loginEditText = findViewById(R.id.login_edittext)
        passwordEditText = findViewById(R.id.password_edittext)
        repeatPasswordEditText = findViewById(R.id.repeat_password_edittext)
        registerButton = findViewById(R.id.register_button)
        loginButton = findViewById(R.id.login_button)

        FirebaseApp.initializeApp(this)
        database = Firebase.database.getReference("Users")
        database.keepSynced(true)

        registerButton.setOnClickListener {
            register()
        }

        loginButton.setOnClickListener {
            val loginIntent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    private fun register(){
        var isAnyEmpty: Boolean = false
        if(loginEditText.text.isNullOrEmpty()){
            loginEditText.error = "Enter login"
            isAnyEmpty = true
        }
        if(passwordEditText.text.isNullOrEmpty()){
            passwordEditText.error = "Enter password"
            isAnyEmpty = true
        }
        if(repeatPasswordEditText.text.isNullOrEmpty()){
            repeatPasswordEditText.error = "Repeat password"
            isAnyEmpty = true
        }
        if(passwordEditText.text.toString() != repeatPasswordEditText.text.toString()){
            Toast.makeText(this, "Password are different", Toast.LENGTH_LONG).show()
            isAnyEmpty = true
        }

        if (isAnyEmpty)
            return

        val passwordHash: String = MessageDigest.getInstance("SHA-512")
            .digest(passwordEditText.text.toString().toByteArray())
            .joinToString(separator = "") {
                ((it.toInt() and 0xff) + 0x100)
                    .toString(16)
                    .substring(1) }

        val newUser = UserModel(
            loginEditText.text.toString(),
            passwordHash,
        )

        val userExistsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (!dataSnapshot.exists()){
                    database.child(newUser.login!!).setValue(newUser)
                    SharedPreferenceManager.saveUser(this@RegisterActivity, newUser)
                }else{
                    Toast.makeText(this@RegisterActivity, "Login already in use", Toast.LENGTH_LONG).show()
                }
                database.orderByChild("login").equalTo(newUser.login).removeEventListener(this)

                val mainIntent = Intent(this@RegisterActivity, MainActivity::class.java
                startActivity(mainIntent)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                database.orderByChild("login").equalTo(newUser.login).removeEventListener(this)
            }
        }

        database.orderByChild("login").equalTo(newUser.login).addValueEventListener(userExistsListener)
    }
}