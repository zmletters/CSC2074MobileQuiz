package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Signin_Activity : AppCompatActivity() {

    private var db: DBHelper? = null

    lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var signin_button: Button
    lateinit var tvRedirectSignUp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)

        db = DBHelper(this, null)
        etUsername = findViewById(R.id.signin_username)
        etPassword = findViewById(R.id.signin_password)
        signin_button = findViewById(R.id.signin_button)
        tvRedirectSignUp = findViewById(R.id.signupRedirectText)

        signin_button.setOnClickListener{
            signInUser()
        }

        tvRedirectSignUp.setOnClickListener {
            val intent = Intent(this, Signup_Activity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        db?.close() // Ensure DBHelper is closed when activity is destroyed
    }

    private fun signInUser() {

        val username = etUsername.text.toString()
        val password = etPassword.text.toString()

        // check password
        if (username.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Email and password cant' be blank", Toast.LENGTH_SHORT).show()
            return
        }

        //SIGN IN THE USER?
        if (db?.signIn(username, password) == true) {
            Toast.makeText(this, "Sign In Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Playermainpage::class.java)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
            return
        } else {
            Toast.makeText(this, "Wrong username or password!", Toast.LENGTH_SHORT).show()
        }
    }
}