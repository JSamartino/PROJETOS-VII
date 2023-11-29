package com.plcoding.composegooglesignincleanarchitecture.presentation.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.plcoding.composegooglesignincleanarchitecture.R

class PinScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_screen)

    }




}

@Composable
fun NumberScreen() {
    var password by remember { mutableStateOf("") }
    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName ?: ""

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // LazyColumn para exibir números
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
        ) {
            items(10) { number ->
                NumberItem(number)
            }
        }

        // Botão para trocar a senha
        Button(
            onClick = {
                // Lógica para trocar a senha
                // Aqui você pode adicionar a lógica para trocar a senha
                // Por exemplo, você pode abrir um diálogo ou navegar para outra tela
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Trocar Senha")
        }
    }
}

@Composable
fun NumberItem(number: Int) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .size(50.dp)
            .clip(CircleShape)
            //.background(MaterialTheme.colorScheme.primary)
            .clickable { /* Ação ao clicar em um número */ }
    ) {
        Text(
            text = number.toString(),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            //  color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.h6
        )
    }
}