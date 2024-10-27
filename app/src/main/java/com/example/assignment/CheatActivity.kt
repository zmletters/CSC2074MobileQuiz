package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CheatActivity : AppCompatActivity() {

    private lateinit var textViewAnswer: TextView
    private lateinit var buttonShowAnswer: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        textViewAnswer = findViewById(R.id.text_view_answer_value)
        buttonShowAnswer = findViewById(R.id.button_show_answer)

        // Retrieve the correct answer from intent extra
        val answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)


        buttonShowAnswer.setOnClickListener {
            // Show the correct answer
            textViewAnswer.text = answerIsTrue.toString()
            setAnswerShownResult(true)

        }
    }

    companion object {
        const val EXTRA_ANSWER_IS_TRUE = "com.example.assignment.answer_is_true"
        const val EXTRA_ANSWER_SHOWN = "com.example.assignment.answer_shown"
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(RESULT_OK, data)
    }
}
