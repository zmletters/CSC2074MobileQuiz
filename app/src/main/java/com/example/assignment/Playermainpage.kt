package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Playermainpage : AppCompatActivity() {
    private lateinit var username_textview: TextView
    private lateinit var startquiz_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_playermainpage)

        username_textview = findViewById(R.id.playermainpage_username)
        startquiz_button = findViewById(R.id.startquiz_button)

        val username = intent.getStringExtra("USERNAME")
        if (username != null) {
            Toast.makeText(this, "Welcome, $username!", Toast.LENGTH_SHORT).show()
            username_textview.setText("Welcome $username!")
        }

        startquiz_button.setOnClickListener {
            // redirect to start the quiz
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("USERNAME", username)
            startActivity(intent)
        }



    }


}