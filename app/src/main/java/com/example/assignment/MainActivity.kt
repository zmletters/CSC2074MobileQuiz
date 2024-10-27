package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {
    private lateinit var signIn_Button: Button
    private lateinit var signUp_Button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_signup)
        setContentView(R.layout.activity_main)
        //val intent = Intent(this, Signin_Activity::class.java)
        //intent.putExtra("SHOW_WELCOME", true)
        //startActivity(intent)

        signIn_Button = findViewById(R.id.btnSignIn)
        signUp_Button = findViewById(R.id.btnSignUp)

        signIn_Button.setOnClickListener {
            val intent = Intent(this, Signin_Activity::class.java)
            startActivity(intent)
        }

        signUp_Button.setOnClickListener {
            val intent = Intent(this, Signup_Activity::class.java)
            startActivity(intent)
        }


    }
}

