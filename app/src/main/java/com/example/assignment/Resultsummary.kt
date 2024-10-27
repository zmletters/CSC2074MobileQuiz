package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Resultsummary : AppCompatActivity() {

    private lateinit var tvUsername: TextView
    private lateinit var tvQuestionAnswered: TextView
    private lateinit var tvTotalScore: TextView
    private lateinit var tvTotalCheatAttempts: TextView
    private lateinit var btn_back_to_main_page: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resultsummary)

        // Initialize TextViews
        tvUsername = findViewById(R.id.summary_username)
        tvQuestionAnswered = findViewById(R.id.summary_question_answered)
        tvTotalScore = findViewById(R.id.summary_total_score)
        tvTotalCheatAttempts = findViewById(R.id.summary_total_cheat_attempts)

        btn_back_to_main_page = findViewById(R.id.summary_back_to_main_page)

        // Retrieve data from intent extras
        val username = intent.getStringExtra("USERNAME")
        val questionAnswered = intent.getIntExtra("TOTAL_QUESTION_ANSWERED", 0)
        val totalScore = intent.getIntExtra("SCORE", 0)
        val totalCheatAttempts = intent.getIntExtra("TOTAL_CHEAT_ATTEMPTS", 0)

        tvUsername.text = username
        tvQuestionAnswered.text = questionAnswered.toString()
        tvTotalScore.text = totalScore.toString()
        tvTotalCheatAttempts.text = totalCheatAttempts.toString()

        btn_back_to_main_page.setOnClickListener {
            finish()
        }

    }
}