package com.budgetiko.budgetikomobile.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
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
fun RegisterScreen(onNavigateToLogin: () -> Unit, onRegisterSuccess: () -> Unit) {
    val context = LocalContext.current
    val repository = remember { BudgetikoRepository.getInstance(context) }
    val scope = rememberCoroutineScope()

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var termsAgreed by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Column(
                modifier = Modifier.fillMaxSize().padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
        ) {
            Text(
                    text = "Create an account",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Green40,
                    modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                    text = "Access your goals, plans and projects anywhere, anytime",
                    fontSize = 16.sp,
                    color = Green40.copy(alpha = 0.8f),
                    modifier = Modifier.padding(bottom = 24.dp)
            )

            // Full Name Field
            Text(
                    "Full name",
                    fontWeight = FontWeight.SemiBold,
                    color = Green40,
                    modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    placeholder = { Text("James Ruby Betito") },
                    leadingIcon = { Icon(Icons.Default.Person, contentDescription = "Name") },
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

            // Email Field
            Text(
                    "Email address",
                    fontWeight = FontWeight.SemiBold,
                    color = Green40,
                    modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("jamesruby.betito@cit.edu") },
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

            // Password field
            Text(
                    "Create password",
                    fontWeight = FontWeight.SemiBold,
                    color = Green40,
                    modifier = Modifier.padding(bottom = 4.dp)
            )
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
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    colors =
                            TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Green40,
                                    unfocusedBorderColor = Green40.copy(alpha = 0.5f),
                                    focusedLabelColor = Green40,
                            ),
                    singleLine = true
            )

            // Confirm password field
            Text(
                    "Confirm password",
                    fontWeight = FontWeight.SemiBold,
                    color = Green40,
                    modifier = Modifier.padding(bottom = 4.dp)
            )
            OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    placeholder = { Text("••••••••") },
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Confirm Password")
                    },
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
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    colors =
                            TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = Green40,
                                    unfocusedBorderColor = Green40.copy(alpha = 0.5f),
                                    focusedLabelColor = Green40,
                            ),
                    singleLine = true
            )

            // Checkbox for terms
            Row(
                    modifier =
                            Modifier.fillMaxWidth()
                                    .padding(bottom = 24.dp)
                                    .toggleable(
                                            value = termsAgreed,
                                            onValueChange = { termsAgreed = !termsAgreed },
                                            role = Role.Checkbox
                                    ),
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                        checked = termsAgreed,
                        onCheckedChange = null,
                        colors = CheckboxDefaults.colors(checkedColor = Green40)
                )
                Spacer(Modifier.width(8.dp))
                Text("I agree to the ", color = Green40.copy(alpha = 0.8f))
                Text("Terms of Service", color = Green40, fontWeight = FontWeight.Bold)
                Text(" and ", color = Green40.copy(alpha = 0.8f))
                Text("Privacy Policy", color = Green40, fontWeight = FontWeight.Bold)
            }

            Button(
                    onClick = {
                        if (fullName.isBlank() ||
                                        email.isBlank() ||
                                        password.isBlank() ||
                                        confirmPassword.isBlank()
                        ) {
                            errorMessage = "All fields are required."
                            return@Button
                        }
                        if (!termsAgreed) {
                            errorMessage =
                                    "Please agree to the Terms of Service and Privacy Policy."
                            return@Button
                        }

                        scope.launch {
                            loading = true
                            errorMessage = null
                            successMessage = null

                            when (val result =
                                            repository.register(
                                                    fullName = fullName.trim(),
                                                    email = email.trim(),
                                                    password = password,
                                                    confirmPassword = confirmPassword
                                            )
                            ) {
                                is ApiResult.Success -> {
                                    successMessage =
                                            result.data.message ?: "Account created successfully."
                                    onRegisterSuccess()
                                }
                                is ApiResult.Error -> {
                                    errorMessage = result.message
                                }
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
                            color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Create account", fontSize = 16.sp)
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

            if (successMessage != null) {
                Spacer(Modifier.height(4.dp))
                Text(text = successMessage ?: "", color = Green40, fontSize = 13.sp)
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

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Already have an account? ", color = Green40.copy(alpha = 0.8f))
                TextButton(onClick = onNavigateToLogin, modifier = Modifier.padding(0.dp)) {
                    Text("Sign in", fontWeight = FontWeight.Bold, color = Green40)
                }
            }
        }
    }
}
