package com.example.unscramble.ui

data class GameUiState(
    val scrambledWord: String = "",
    val currentWordCount: Int = 0,
    val score: Int = 0,
    val isGameOver: Boolean = false
)
