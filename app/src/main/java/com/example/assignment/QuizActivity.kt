package com.example.assignment

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.max
import kotlin.math.min

class QuizActivity : AppCompatActivity() {
    private val TAG: String = "QuizActivity"
    //private lateinit var tvTimer: TextView
    private lateinit var game_question_txt: TextView
    private lateinit var game_true_btn: Button
    private lateinit var game_false_btn: Button
    private lateinit var game_next_btn: Button
    private lateinit var game_previous_btn: Button
    private lateinit var game_reset_btn: Button
    private lateinit var game_cheat_btn: Button
    private lateinit var tvTimer: TextView
    private var question_current_index = 0
    private var score: Int = 0
    private var cheatAttempts: Int = 0
    private var timer: CountDownTimer? = null
    private val TIMER_DURATION: Long = 60000



    val questionList = arrayOf(
        Question(R.string.q1_australia,false),
        Question(R.string.q2_china, true),
        Question(R.string.q3_belgium, true),
        Question(R.string.q4_san_marino, true)
    )

    private lateinit var startCheatActivityForResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        // Shuffle question
        questionList.shuffle()

        val questionTotal: Int = questionList.size

        // find View
        game_question_txt = findViewById(R.id.game_question)
        game_true_btn = findViewById(R.id.game_true_btn)
        game_false_btn = findViewById(R.id.game_false_btn)
        game_next_btn = findViewById(R.id.game_next_btn)
        game_previous_btn = findViewById(R.id.game_previous_btn)
        game_reset_btn = findViewById(R.id.game_reset_btn)
        game_cheat_btn = findViewById(R.id.game_cheat_btn)
        tvTimer = findViewById(R.id.tv_timer)

        // Start timer
        startTimer()

        // Event
        game_true_btn.setOnClickListener {
            game_false_btn.isEnabled = false
            game_false_btn.isClickable = false
            questionList[question_current_index].setHasBeenAnswered(true)
            checkAnswer(true)
        }

        game_false_btn.setOnClickListener {
            game_true_btn.isEnabled = false
            game_true_btn.isEnabled = false
            questionList[question_current_index].setHasBeenAnswered(true)
            checkAnswer(false)
        }

        game_next_btn.setOnClickListener {
            if (question_current_index == questionList.size - 1) {
                Log.d(TAG, "$score")
                //var toasttext =  Toast.makeText(this, "Result is: $score", Toast.LENGTH_LONG)
                //toasttext.show()
                showResult()
            } else {
                question_current_index = min(question_current_index + 1, questionTotal-1)
                updateQuestion()

            }
        }

        game_previous_btn.setOnClickListener {
            question_current_index = max(question_current_index - 1, 0)
            updateQuestion()
        }

        game_reset_btn.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            finish()
            startActivity(intent)
        }

        game_cheat_btn.setOnClickListener {
            if (cheatAttempts < 3) {
                val answerIsTrue = questionList[question_current_index].isAnswerTrue()
                val intent = Intent(this, CheatActivity::class.java)
                intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                startCheatActivityForResult.launch(intent)
            } else {
                Toast.makeText(this, "No more cheat attempts remaining!", Toast.LENGTH_SHORT).show()
                game_cheat_btn.isEnabled = false
            }
        }

        // Initialize the ActivityResultLauncher
        startCheatActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Handle cheat result ++ to result summary
                cheatAttempts++
                Toast.makeText(this, "Cheat attempt registered!", Toast.LENGTH_SHORT).show()
            }
        }

        updateQuestion()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(TIMER_DURATION, 1000) { // CountDownTimer with 1 second interval
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                tvTimer.text = "Time left: ${seconds}s"
            }

            override fun onFinish() {
                tvTimer.text = "Time's up!"
                showResult()
            }
        }.start()
    }


    private fun updateQuestion() {
        val question = questionList[question_current_index].getTextResID()
        game_question_txt.setText(question)

        // if the question has been answered then remain make sure the button is still being disabled
        if (!questionList[question_current_index].getHasBeenAnswered()) {
            resetButton()
        }

        if (question_current_index == questionList.size - 1) {
            game_next_btn.text = "Finish"
        } else {
            game_next_btn.text = "Next"
        }
    }

    private fun resetButton() {
        game_true_btn.isEnabled = true
        game_true_btn.isClickable = true
        game_false_btn.isEnabled = true
        game_false_btn.isClickable = true
    }

    private fun checkAnswer(userChoice: Boolean) {
        var correctAnswer: Boolean = questionList[question_current_index].isAnswerTrue()
        if (userChoice == correctAnswer) {
            score += 1
            Log.d(TAG, "User chose $userChoice vs $correctAnswer")
        }
    }

    private fun showResult() {
        val percentageScore = (score.toDouble() / questionList.size.toDouble()) * 100
        val resultMessage = "Your Percentage Score: ${"%.2f".format(percentageScore)}%"
        Toast.makeText(this, resultMessage, Toast.LENGTH_LONG).show()



        // redirect to result summary page
        val intent = Intent(this, Resultsummary::class.java)
        //val username = intent.getStringExtra("USERNAME")
        intent.putExtra("USERNAME", getUsername())
        intent.putExtra("SCORE", score)
        intent.putExtra("TOTAL_QUESTION_ANSWERED", questionList.size)
        intent.putExtra("TOTAL_CHEAT_ATTEMPTS", cheatAttempts)
        startActivity(intent)
        finish()
    }

    private fun getUsername(): String {
        val username = intent.getStringExtra("USERNAME")
        return username.toString()
    }



}