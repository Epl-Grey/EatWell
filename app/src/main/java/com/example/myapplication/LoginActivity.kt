package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.firebase.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {
    lateinit var loginEditText: EditText
    lateinit var passwordEditText: EditText
    lateinit var loginButton: Button
    lateinit var registerButton: TextView

    lateinit var saveState: SaveState

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEditText = findViewById(R.id.login_edittext)
        passwordEditText = findViewById(R.id.password_edittext)
        loginButton = findViewById(R.id.login_button)
        registerButton = findViewById(R.id.register_button)

        database = Firebase.database.getReference("Users")
        database.keepSynced(true)

        saveState = SaveState(this, "ob")

        loginButton.setOnClickListener {
            login()
        }

        registerButton.setOnClickListener {
            val registerIntent = Intent(this@LoginActivity, RegisterAimActivity::class.java)
            startActivity(registerIntent)
        }
    }

    private fun login() {
        var isAnyEmpty: Boolean = false
        if(loginEditText.text.isNullOrEmpty()){
            loginEditText.error = "Enter login"
            isAnyEmpty = true
        }
        if(passwordEditText.text.isNullOrEmpty()){
            passwordEditText.error = "Enter password"
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
                passwordHash
        )

        val userExistsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                if (dataSnapshot.exists()){
                    SharedPreferenceManager.saveUser(this@LoginActivity, newUser)

                    Toast.makeText(this@LoginActivity, "Welcome!", Toast.LENGTH_LONG).show()

                    val mainIntent = Intent(this@LoginActivity, MainActivity::class.java)
                    saveState.state = 2
                    startActivity(mainIntent)
                    finish()
                }else{
                    Toast.makeText(this@LoginActivity, "Incorrect auth data", Toast.LENGTH_LONG).show()
                }
                database.orderByChild("login").equalTo(newUser.login).removeEventListener(this)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                database.orderByChild("login").equalTo(newUser.login).removeEventListener(this)
            }
        }

        database.orderByChild("login").equalTo(newUser.login).addValueEventListener(userExistsListener)
    }
}