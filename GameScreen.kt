package com.example.unscramble.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    var userGuess by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Palabra desordenada: ${uiState.scrambledWord}", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = userGuess,
            onValueChange = { userGuess = it },
            label = { Text("Tu respuesta") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            viewModel.onUserGuess(userGuess)
            userGuess = ""
        }) {
            Text("Enviar")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Puntaje: ${uiState.score}")
        Text("Palabra #${uiState.currentWordCount}")

        if (uiState.isGameOver) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("¡Juego terminado!", style = MaterialTheme.typography.headlineMedium)
            Button(onClick = { viewModel.resetGame() }) {
                Text("Jugar de nuevo")
            }
        }
    }
}
