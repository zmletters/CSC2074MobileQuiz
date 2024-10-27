package com.example.assignment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :

    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "SignLog.db"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "users"
        const val USERNAME_COL = "username"
        const val PASSWORD_COL = "password"
        const val TOTAL_QUESTION_ANSWERED = "total_question_answered"
        const val TOTAL_SCORE = "total_score"
        const val TOTAL_CHEAT_ATTEMPT = "total_cheat_attempt"
        private val TAG = "DBHelper"
    }
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME (" +
                "$USERNAME_COL TEXT," + "$PASSWORD_COL TEXT)")
        db.execSQL(createTable)
        Log.d(TAG, "Database created.")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        if (db != null) {
            onCreate(db)
        }
    }

    fun insertUser(username: String, password: String): Boolean {
        Log.d(TAG, "inserting User")
        val db = this.writableDatabase
        val values = ContentValues()
        Log.d(TAG, "putting values")
        values.put(USERNAME_COL, username)
        values.put(PASSWORD_COL, password)
        Log.d(TAG, "$username $password")
        val result = db.insert("users", null, values)

        Log.d(TAG, (result.toInt() != -1).toString())
        // return false if result != -1
        if (result.toInt() == -1) {
            db.close()
            return false
        } else {
            db.close()
            return true
        }
    }

    @SuppressLint("Recycle")
    fun checkUsername(username: String): Boolean {
        val db = this.writableDatabase
        val cursor = db.rawQuery("Select * from $TABLE_NAME where username = ?", arrayOf(username))

        if (cursor.count > 0) {

            cursor.close()
            db.close()
            return true
        } else {

            cursor.close()
            db.close()
            return false
        }
    }

    @SuppressLint("Recycle")
    fun signIn(username: String, password: String): Boolean {
        val db = this.writableDatabase
        //val cursor = db.rawQuery("Select * from $TABLE_NAME where $USERNAME_COL = ? and $PASSWORD_COL = ?", arrayOf(username, password))
        val cursor = db.query(TABLE_NAME,null, "$USERNAME_COL = ? and $PASSWORD_COL = ?", arrayOf(username,password),null,null,null)
        if (cursor.count > 0) {
            cursor.close()
            db.close()
            return true
        } else {
            cursor.close()
            db.close()
            return false
        }
    }


}
