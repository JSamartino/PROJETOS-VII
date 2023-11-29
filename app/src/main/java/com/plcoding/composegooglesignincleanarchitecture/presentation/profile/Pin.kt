package com.plcoding.composegooglesignincleanarchitecture.presentation.profile

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.plcoding.composegooglesignincleanarchitecture.ui.theme.ComposeGoogleSignInCleanArchitectureTheme

class Pin : ComponentActivity() {
    // Declare enteredNumbers fora do método onCreate ou de outras funções
    var enteredNumbers by mutableStateOf("")
    var enteredId by mutableStateOf("")
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val reference = database.getReference("users")
    private val total = database.getReference("users/totalUsers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            // Configurar e exibir a tela com TextField e botão salvar
            SavePasswordScreen()
        }
    }

    @Composable
    fun SavePasswordScreen() {
        var password by remember { mutableStateOf("") }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // TextField for entering ID
            TextField(
                value = enteredId,
                onValueChange = {
                    // Update the entered ID
                    enteredId = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .background(Color.Gray)
                    .padding(16.dp),
                textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text("Login") } // Optional: You can add a label for the TextField
            )

            // TextField for entering numbers
            TextField(
                value = enteredNumbers,
                onValueChange = {
                    // Update the entered numbers
                    enteredNumbers = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 80.dp,
                        start = 16.dp,
                        end = 16.dp
                    ) // Adjust the top padding to separate the two TextFields
                    .background(Color.Gray)
                    .padding(16.dp),
                textStyle = TextStyle(color = Color.White, fontSize = 20.sp),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text("Senha") } // Optional: You can add a label for the TextField
            )

            // Button to save the password
            Button(
                onClick = {
                    password = enteredNumbers

                    val userId = enteredId.take(4) // Sequência definida como chave
                    val userPassword =
                        enteredNumbers.take(4) // Ajuste o comprimento conforme necessário

                    total.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val totalValue = snapshot.value as? Long ?: 0

                            // Agora, você pode usar o valor de 'totalValue' conforme necessário
                            val quantidade = totalValue + 1

                            // Exemplo: Salvar em um nó "users" com o userId como chave
                            val userReference = reference.child(quantidade.toString())
                            userReference.child("username").setValue(userId)
                            userReference.child("password").setValue(userPassword)
                            reference.child("/totalUsers").setValue(quantidade)
                            userReference.child("type").setValue(0)


                            // Exibir uma mensagem de sucesso, por exemplo, um Toast
                            Toast.makeText(
                                applicationContext,
                                "Senha salva com sucesso",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Lidar com erro de banco de dados
                        }
                    })
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                Text(text = "Salvar Senha")
            }


        }
    }
}

