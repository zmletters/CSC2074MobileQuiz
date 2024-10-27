package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

// https://www.geeksforgeeks.org/login-and-registration-in-android-using-firebase-in-kotlin/
// https://androidknowledge.com/login-signup-sqlite-android-studio-java/
class Signup_Activity : AppCompatActivity() {
    //private lateinit var db: DatabaseHelper
    private var db: DBHelper? = null

    private val TAG = "Signup_Activity"
    private lateinit var etUsername: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var etPassword: EditText
    private lateinit var signup_button: Button
    lateinit var tvRedirectLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)

        etUsername = findViewById(R.id.signup_username)
        etPassword = findViewById(R.id.signup_password)
        etConfirmPassword = findViewById(R.id.signup_confirmpassword)
        signup_button = findViewById(R.id.signup_button)
        tvRedirectLogin = findViewById(R.id.loginRedirectText)

        signup_button.setOnClickListener {
            signUpUser()
        }

        // redirecting user from sign up page to login page
        tvRedirectLogin.setOnClickListener {
            val intent = Intent(this, Signin_Activity::class.java)
            startActivity(intent)
        }

    }

    private fun signUpUser() {
        val db = DBHelper(this,null)
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        val confirmPassword = etConfirmPassword.text.toString()

        // check password
        if (username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Email and password cant' be blank", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Password and Confirm Password is not the same!", Toast.LENGTH_SHORT).show()
            return
        }

        if (db?.checkUsername(username) == true) {
            Toast.makeText(this, "Username has been used!", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d(TAG, "trying to sign up")
        Log.d(TAG, "$username $password $confirmPassword" )

        // Insert User
        val insertUser = db?.insertUser(username, password)
        Log.d(TAG, insertUser.toString())
        if ( insertUser == true) {
            Log.d(TAG, "Successfully signed up!")
            Toast.makeText(this,"Successfully signed up!",Toast.LENGTH_SHORT).show()
            finish()
            return
        } else {
            Toast.makeText(this, "Sign up failed! Please try again", Toast.LENGTH_SHORT).show()
        }

       return
    }
}
