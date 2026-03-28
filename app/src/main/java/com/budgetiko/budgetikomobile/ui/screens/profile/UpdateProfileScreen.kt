package com.budgetiko.budgetikomobile.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.budgetiko.budgetikomobile.ui.theme.Green40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProfileScreen(
    onNavigateBack: () -> Unit
) {
    var fullName by remember { mutableStateOf("James Ruby Betito") }
    var email by remember { mutableStateOf("jamesruby.betito@cit.edu") }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CenterAlignedTopAppBar(
                title = { Text("Update Profile", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = Green40,
                    navigationIconContentColor = Green40
                )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text("Full name", fontWeight = FontWeight.SemiBold, color = Green40, modifier = Modifier.padding(bottom = 4.dp))
                OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Green40,
                        unfocusedBorderColor = Green40.copy(alpha = 0.5f),
                        focusedLabelColor = Green40,
                    ),
                    singleLine = true
                )

                Text("Email address", fontWeight = FontWeight.SemiBold, color = Green40, modifier = Modifier.padding(bottom = 4.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 32.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Green40,
                        unfocusedBorderColor = Green40.copy(alpha = 0.5f),
                        focusedLabelColor = Green40,
                    ),
                    singleLine = true
                )

                Button(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Green40),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Save Changes", fontSize = 16.sp)
                }
            }
        }
    }
}
