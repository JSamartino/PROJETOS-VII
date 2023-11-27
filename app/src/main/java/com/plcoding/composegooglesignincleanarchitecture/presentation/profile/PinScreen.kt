package com.plcoding.composegooglesignincleanarchitecture.presentation.profile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.LocalViewConfiguration

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.plcoding.composegooglesignincleanarchitecture.R
import com.plcoding.composegooglesignincleanarchitecture.presentation.sign_in.UserData
import com.plcoding.composegooglesignincleanarchitecture.ui.theme.ComposeGoogleSignInCleanArchitectureTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeGoogleSignInCleanArchitectureTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                 //   color = MaterialTheme.colorScheme.background
                ) {
                    // Call your Composable function here
                    NumberScreen()
                }
            }
        }
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
