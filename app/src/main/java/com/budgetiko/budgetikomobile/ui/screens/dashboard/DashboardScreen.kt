package com.budgetiko.budgetikomobile.ui.screens.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.budgetiko.budgetikomobile.ui.theme.Mint80
import com.budgetiko.budgetikomobile.ui.theme.GreenGrey80

@Composable
fun DashboardScreen() {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                Text("Dashboard", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Green40)
                Text("Welcome back! Here's your financial overview.", color = Green40.copy(alpha = 0.8f))
                Spacer(Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    SummaryCard(title = "Balance", amount = "$4690.00", subtitle = "Current available", icon = Icons.Default.AccountBalanceWallet, color = Green40, modifier = Modifier.weight(1f))
                    SummaryCard(title = "Income", amount = "$5000.00", subtitle = "Total income", icon = Icons.Default.TrendingUp, color = Green40, modifier = Modifier.weight(1f))
                }
                Spacer(Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    SummaryCard(title = "Expenses", amount = "$310.00", subtitle = "Total expenses", icon = Icons.Default.TrendingDown, color = Color.Red, modifier = Modifier.weight(1f))
                    SummaryCard(title = "Unpaid Bills", amount = "2", subtitle = "Due soon", icon = Icons.Default.Receipt, color = Color(0xFFFF9800), modifier = Modifier.weight(1f))
                }
                Spacer(Modifier.height(16.dp))

                // Chart Placeholders
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Budget Progress", fontWeight = FontWeight.Bold, color = Green40, modifier = Modifier.padding(bottom = 16.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(150.dp), contentAlignment = Alignment.Center) {
                            Text("Bar Chart Placeholder", color = Color.Gray)
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Expenses by Category", fontWeight = FontWeight.Bold, color = Green40, modifier = Modifier.padding(bottom = 16.dp))
                        Box(modifier = Modifier.fillMaxWidth().height(150.dp), contentAlignment = Alignment.Center) {
                            Text("Pie Chart Placeholder", color = Color.Gray)
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Recent Transactions", fontWeight = FontWeight.Bold, color = Green40, modifier = Modifier.padding(bottom = 16.dp))
                        TransactionItem("Gas Station", "Transportation • Mar 4", "-$60.00", Color.Red, Icons.Default.LocalGasStation)
                        TransactionItem("Grocery Shopping", "Food & Dining • Mar 2", "-$250.00", Color.Red, Icons.Default.ShoppingCart)
                        TransactionItem("Monthly Salary", "Salary • Mar 1", "+$5000.00", Green40, Icons.Default.AttachMoney)
                    }
                }
                Spacer(Modifier.height(64.dp)) // padding for bottom nav
            }
        }
    }
}

@Composable
fun SummaryCard(title: String, amount: String, subtitle: String, icon: androidx.compose.ui.graphics.vector.ImageVector, color: Color, modifier: Modifier = Modifier) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(title, color = Green40.copy(alpha = 0.8f))
                Icon(icon, contentDescription = null, tint = Green40.copy(alpha = 0.5f), modifier = Modifier.size(20.dp))
            }
            Text(amount, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = color, modifier = Modifier.padding(vertical = 8.dp))
            Text(subtitle, fontSize = 12.sp, color = Green40.copy(alpha = 0.6f))
        }
    }
}

@Composable
fun TransactionItem(title: String, subtitle: String, amount: String, amountColor: Color, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .padding(end = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = Green40)
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, color = Green40)
            Text(subtitle, fontSize = 12.sp, color = Green40.copy(alpha = 0.6f))
        }
        Text(amount, fontWeight = FontWeight.Bold, color = amountColor)
    }
}
