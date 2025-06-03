package com.example.unscramble.ui

import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState

    private var usedWords = mutableSetOf<String>()
    private lateinit var currentWord: String

    init {
        resetGame()
    }

    fun resetGame() {
        usedWords.clear()
        pickNewWord()
        _uiState.value = GameUiState(
            scrambledWord = shuffleWord(currentWord),
            currentWordCount = 1,
            score = 0,
            isGameOver = false
        )
    }

    fun onUserGuess(guess: String) {
        val correct = guess.equals(currentWord, ignoreCase = true)
        val scoreIncrement = if (correct) 10 else 0

        if (_uiState.value.currentWordCount == MAX_NO_OF_WORDS) {
            _uiState.update {
                it.copy(
                    score = it.score + scoreIncrement,
                    isGameOver = true
                )
            }
        } else {
            pickNewWord()
            _uiState.update {
                it.copy(
                    scrambledWord = shuffleWord(currentWord),
                    currentWordCount = it.currentWordCount + 1,
                    score = it.score + scoreIncrement
                )
            }
        }
    }

    private fun pickNewWord() {
        do {
            currentWord = allWords.random()
        } while (usedWords.contains(currentWord))
        usedWords.add(currentWord)
    }

    private fun shuffleWord(word: String): String {
        val chars = word.toCharArray()
        chars.shuffle()
        return String(chars)
    }

    companion object {
        private const val MAX_NO_OF_WORDS = 10
    }
}
