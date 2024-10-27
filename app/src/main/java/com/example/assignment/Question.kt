package com.example.assignment

class Question(textResID: Int, answerTrue: Boolean) {

    private var textResID: Int = textResID
    private var answerTrue: Boolean = answerTrue
    private var hasBeenAnswered: Boolean = false

    public fun getTextResID(): Int {
        return textResID
    }

    public fun setTextResID(textResID: Int) {
        this.textResID = textResID
    }

    public fun isAnswerTrue(): Boolean {
        return this.answerTrue
    }

    public fun setAnswerTrue(answerTrue: Boolean) {
        this.answerTrue = answerTrue
    }

    public fun getHasBeenAnswered(): Boolean {
        return hasBeenAnswered
    }

    public fun setHasBeenAnswered(bool: Boolean) {
        this.hasBeenAnswered = bool
    }

}