package com.budgetiko.budgetikomobile.ui.screens.auth

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.budgetiko.budgetikomobile.data.network.ApiResult
import com.budgetiko.budgetikomobile.data.network.BudgetikoRepository
import com.budgetiko.budgetikomobile.ui.theme.Green40
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onNavigateToRegister: () -> Unit, onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    val repository = remember { BudgetikoRepository.getInstance(context) }
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
        ) {
            Text(
                    text = "Sign in to your account",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green40,
                    modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                    text = "Access your financial dashboard and insights",
                    fontSize = 16.sp,
                    color = Green40.copy(alpha = 0.8f),
                    modifier = Modifier.padding(bottom = 32.dp)
            )

            Text(
                    "Email address",
                    fontWeight = FontWeight.SemiBold,
                    color = Green40,
                    modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("your.email@example.com") },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email") },
                    enabled = !loading,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    colors =
                            TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Green40,
                                    unfocusedBorderColor = Green40.copy(alpha = 0.5f),
                                    focusedLabelColor = Green40,
                            ),
                    singleLine = true
            )

            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                        "Password",
                        fontWeight = FontWeight.SemiBold,
                        color = Green40,
                        modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                        "Forgot password?",
                        color = Green40,
                        modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    placeholder = { Text("••••••••") },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password") },
                    trailingIcon = {
                        val image =
                                if (passwordVisible) Icons.Default.VisibilityOff
                                else Icons.Default.Visibility
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(image, "Toggle password visibility")
                        }
                    },
                    visualTransformation =
                            if (passwordVisible) VisualTransformation.None
                            else PasswordVisualTransformation(),
                    enabled = !loading,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                    colors =
                            TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Green40,
                                    unfocusedBorderColor = Green40.copy(alpha = 0.5f),
                                    focusedLabelColor = Green40,
                            ),
                    singleLine = true
            )

            Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            errorMessage = "Email and password are required."
                            return@Button
                        }

                        scope.launch {
                            loading = true
                            errorMessage = null
                            when (val result = repository.login(email.trim(), password)) {
                                is ApiResult.Success -> onLoginSuccess()
                                is ApiResult.Error -> errorMessage = result.message
                            }
                            loading = false
                        }
                    },
                    enabled = !loading,
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Green40),
                    shape = RoundedCornerShape(8.dp)
            ) {
                if (loading) {
                    CircularProgressIndicator(
                            modifier = Modifier.size(18.dp),
                            strokeWidth = 2.dp,
                            color = Color.White
                    )
                } else {
                    Text("Sign in", fontSize = 16.sp)
                }
            }

            if (errorMessage != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(32.dp))

            Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
            ) {
                Divider(modifier = Modifier.weight(1f), color = Green40.copy(alpha = 0.2f))
                Text(" Or continue with ", color = Green40.copy(alpha = 0.8f))
                Divider(modifier = Modifier.weight(1f), color = Green40.copy(alpha = 0.2f))
            }

            Spacer(Modifier.height(32.dp))

            Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
            ) {
                SocialButton(icon = Icons.Default.AccountCircle)
                SocialButton(icon = Icons.Default.Code)
                SocialButton(icon = Icons.Default.Face)
            }

            Spacer(Modifier.height(32.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Don't have an account? ", color = Green40.copy(alpha = 0.8f))
                TextButton(onClick = onNavigateToRegister, modifier = Modifier.padding(0.dp)) {
                    Text("Create account", fontWeight = FontWeight.Bold, color = Green40)
                }
            }
        }
    }
}

@Composable
fun SocialButton(icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Box(
            modifier =
                    Modifier.width(100.dp)
                            .height(50.dp)
                            .border(1.dp, Green40.copy(alpha = 0.3f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
    ) { Icon(icon, contentDescription = null, tint = Green40) }
}
