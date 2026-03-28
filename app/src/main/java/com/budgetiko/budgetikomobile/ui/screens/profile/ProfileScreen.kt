package com.budgetiko.budgetikomobile.ui.screens.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.budgetiko.budgetikomobile.ui.theme.Green40

@Composable
fun ProfileScreen(
    onNavigateToUpdateProfile: () -> Unit,
    onNavigateToChangePassword: () -> Unit,
    onLogout: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Green40,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 32.dp)) {
                Icon(Icons.Default.AccountCircle, contentDescription = "Profile Picture", tint = Green40, modifier = Modifier.size(80.dp))
                Spacer(Modifier.width(16.dp))
                Column {
                    Text("James Ruby Betito", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Green40)
                    Text("jamesruby.betito@cit.edu", color = Green40.copy(alpha = 0.8f))
                }
            }

            Divider(color = Green40.copy(alpha = 0.2f), modifier = Modifier.padding(bottom = 16.dp))

            ProfileMenuItem(
                icon = Icons.Default.Edit,
                title = "Update Profile",
                onClick = onNavigateToUpdateProfile
            )
            ProfileMenuItem(
                icon = Icons.Default.Lock,
                title = "Change Password",
                onClick = onNavigateToChangePassword
            )
            ProfileMenuItem(
                icon = Icons.Default.ExitToApp,
                title = "Logout",
                onClick = onLogout,
                color = Color.Red
            )
        }
    }
}

@Composable
fun ProfileMenuItem(icon: androidx.compose.ui.graphics.vector.ImageVector, title: String, onClick: () -> Unit, color: Color = Green40) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp)
    ) {
        Icon(icon, contentDescription = title, tint = color, modifier = Modifier.padding(end = 16.dp))
        Text(title, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = color)
        Spacer(Modifier.weight(1f))
        Icon(Icons.Default.ChevronRight, contentDescription = "Go", tint = color.copy(alpha = 0.5f))
    }
}
