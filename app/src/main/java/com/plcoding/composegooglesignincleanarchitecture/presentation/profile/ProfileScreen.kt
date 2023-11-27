package com.plcoding.composegooglesignincleanarchitecture.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import coil.compose.rememberImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.plcoding.composegooglesignincleanarchitecture.R
import com.plcoding.composegooglesignincleanarchitecture.presentation.sign_in.UserData

@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignOut: () -> Unit,
) {
    val context = LocalContext.current

    var isOpen by remember { mutableStateOf(true) }
    var isDropdownVisible by remember { mutableStateOf(false) }
    val user = FirebaseAuth.getInstance().currentUser
    val userName = user?.displayName ?: ""
    val database2 = FirebaseDatabase.getInstance()
    val reference2 = database2.getReference("userName")
    reference2.setValue(userName)

    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("isOpen")

    reference.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            isOpen = dataSnapshot.getValue(Boolean::class.java) ?: false
        }

        override fun onCancelled(databaseError: DatabaseError) {
            // Lidar com erro de banco de dados
        }
    })

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Botão
            Button(
                onClick = {
                    isDropdownVisible = true
                },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                )
            }

            // DropdownMenu para a lista suspensa
            DropdownMenu(
                expanded = isDropdownVisible,
                onDismissRequest = { isDropdownVisible = false },
            ) {
                DropdownMenuItem(
                    onClick = {
                        isDropdownVisible = false
                        // Navegar para "pinScreen" sem depender de navController
                        // (ajuste a navegação conforme necessário)
//                         //findNavController().navigate("PinScreen")
                    }
                ) {
                    Text("PIN")
                }
                DropdownMenuItem(
                    onClick = {
                        isDropdownVisible = false
                        // Lógica quando a opção "Histórico" é selecionada
                        // Adicione sua lógica aqui
                    }
                ) {
                    Text("Histórico")
                }
            }

            // Imagem no canto superior direito
            if (userData?.profilePictureUrl != null) {
                Image(
                    painter = rememberImagePainter(userData.profilePictureUrl),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .align(Alignment.TopEnd),
                    contentScale = ContentScale.Crop
                )
            }

            // Botão de ABERTO/FECHADO no meio com borda branca
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 16.dp)
            ) {
                Button(
                    onClick = {
                        isOpen = !isOpen
                        reference.setValue(isOpen)
                    },
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .shadow(10.dp, shape = CircleShape)
                        .background(MaterialTheme.colors.primary)
                ) {
                    if (isOpen) {
                        Image(
                            painter = painterResource(R.drawable.ic_open),
                            contentDescription = "Open door icon",
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.ic_closed),
                            contentDescription = "Closed door icon",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Seja bem-vindo ${userData?.username ?: ""}",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            Button(
                onClick = onSignOut,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(top = 16.dp)
                    .background(MaterialTheme.colors.primary)
            ) {
                Text(text = "Sair")
            }
        }
    }
}
