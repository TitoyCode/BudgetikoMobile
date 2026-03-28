package com.budgetiko.budgetikomobile.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.budgetiko.budgetikomobile.data.network.ApiResult
import com.budgetiko.budgetikomobile.data.network.BudgetikoRepository
import com.budgetiko.budgetikomobile.data.network.model.ProfileUser
import com.budgetiko.budgetikomobile.ui.theme.Green40
import kotlinx.coroutines.launch

private data class NavItem(val title: String, val route: String, val icon: ImageVector)

private const val RouteHome = "home"
private const val RouteTransactions = "transactions"
private const val RouteBills = "bills"
private const val RouteBudgets = "budgets"
private const val RouteReports = "reports"
private const val RouteCategories = "categories"
private const val RouteSettings = "settings"
private const val RouteProfile = "profile"
private const val RouteUpdateProfile = "update_profile"
private const val RouteChangePassword = "change_password"
private const val RouteNotifications = "notifications"
private const val RoutePreferences = "preferences"
private const val RouteDataManagement = "data_management"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onLogout: () -> Unit) {
    val context = LocalContext.current
    val repository = remember { BudgetikoRepository.getInstance(context) }
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: RouteHome
    var currentUser by remember { mutableStateOf<ProfileUser?>(null) }

    LaunchedEffect(Unit) {
        when (val result = repository.getProfile()) {
            is ApiResult.Success -> currentUser = result.data.user
            is ApiResult.Error -> Unit
        }
    }

    val displayName = currentUser?.fullName?.takeIf { it.isNotBlank() } ?: "Budgetiko User"
    val displayEmail = currentUser?.email?.takeIf { it.isNotBlank() } ?: "No email"

    val bottomNavItems =
            listOf(
                    NavItem("Home", RouteHome, Icons.Default.Home),
                    NavItem("Transactions", RouteTransactions, Icons.Default.ReceiptLong),
                    NavItem("Bills", RouteBills, Icons.Default.Description),
                    NavItem("Budgets", RouteBudgets, Icons.Default.Savings)
            )

    val drawerItems =
            listOf(
                    NavItem("Dashboard", RouteHome, Icons.Default.Home),
                    NavItem("Transactions", RouteTransactions, Icons.Default.ReceiptLong),
                    NavItem("Bills", RouteBills, Icons.Default.Description),
                    NavItem("Budgets", RouteBudgets, Icons.Default.Savings),
                    NavItem("Reports", RouteReports, Icons.Default.BarChart),
                    NavItem("Categories", RouteCategories, Icons.Default.FolderOpen),
                    NavItem("Settings", RouteSettings, Icons.Default.Settings)
            )

    ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                        drawerShape = RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp),
                        modifier = Modifier.width(320.dp)
                ) {
                    Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AppLogo()
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                    "Budgetiko",
                                    fontSize = 28.sp,
                                    color = Green40,
                                    fontWeight = FontWeight.Bold
                            )
                        }
                        IconButton(onClick = { scope.launch { drawerState.close() } }) {
                            Icon(
                                    Icons.Default.ChevronLeft,
                                    contentDescription = "Close",
                                    tint = Green40
                            )
                        }
                    }

                    Card(
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF6EF)),
                            modifier = Modifier.padding(horizontal = 12.dp).fillMaxWidth()
                    ) {
                        Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                    modifier =
                                            Modifier.size(48.dp)
                                                    .background(Color(0xFFD8EADF), CircleShape),
                                    contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                        Icons.Default.Person,
                                        contentDescription = null,
                                        tint = Green40
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(displayName, color = Green40, fontWeight = FontWeight.SemiBold)
                                Text(
                                        displayEmail,
                                        color = Green40.copy(alpha = 0.8f),
                                        fontSize = 13.sp
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    drawerItems.forEach { item ->
                        val selected = item.route == currentRoute
                        Row(
                                modifier =
                                        Modifier.fillMaxWidth()
                                                .padding(horizontal = 12.dp, vertical = 4.dp)
                                                .background(
                                                        color =
                                                                if (selected) Color(0xFFE5F4E9)
                                                                else Color.Transparent,
                                                        shape = RoundedCornerShape(12.dp)
                                                )
                                                .clickable {
                                                    navController.navigate(item.route) {
                                                        launchSingleTop = true
                                                    }
                                                    scope.launch { drawerState.close() }
                                                }
                                                .padding(horizontal = 14.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(item.icon, contentDescription = item.title, tint = Green40)
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(item.title, color = Green40, fontSize = 18.sp)
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    Divider(color = Color(0xFFD3E8DA))

                    Row(
                            modifier =
                                    Modifier.fillMaxWidth()
                                            .clickable {
                                                scope.launch { drawerState.close() }
                                                onLogout()
                                            }
                                            .padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                                Icons.Default.Logout,
                                contentDescription = "Sign out",
                                tint = Color(0xFFE53935)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Sign out", color = Color(0xFFE53935), fontSize = 24.sp)
                    }
                }
            }
    ) {
        Scaffold(
                topBar = {
                    TopAppBar(
                            title = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    AppLogo()
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Budgetiko", color = Green40, fontWeight = FontWeight.Bold)
                                }
                            },
                            actions = {
                                IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                    Icon(
                                            Icons.Default.Menu,
                                            contentDescription = "Menu",
                                            tint = Green40
                                    )
                                }
                            }
                    )
                },
                bottomBar = {
                    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                        bottomNavItems.forEach { item ->
                            NavigationBarItem(
                                    selected = selectedBottomRoute(currentRoute) == item.route,
                                    onClick = {
                                        navController.navigate(item.route) {
                                            launchSingleTop = true
                                        }
                                    },
                                    icon = { Icon(item.icon, contentDescription = item.title) },
                                    label = { Text(item.title) },
                                    colors =
                                            NavigationBarItemDefaults.colors(
                                                    selectedIconColor = Green40,
                                                    selectedTextColor = Green40,
                                                    indicatorColor = Color(0xFFE4F5E9),
                                                    unselectedIconColor =
                                                            Green40.copy(alpha = 0.55f),
                                                    unselectedTextColor =
                                                            Green40.copy(alpha = 0.55f)
                                            )
                            )
                        }
                    }
                }
        ) { innerPadding ->
            NavHost(
                    navController = navController,
                    startDestination = RouteHome,
                    modifier = Modifier.padding(innerPadding)
            ) {
                composable(RouteHome) { HomeScreen(currentUser) }
                composable(RouteTransactions) { TransactionsScreen() }
                composable(RouteBills) { BillsScreen() }
                composable(RouteBudgets) { BudgetLimitsScreen() }
                composable(RouteReports) { ReportsScreen() }
                composable(RouteCategories) { CategoriesScreen() }

                composable(RouteSettings) {
                    SettingsScreen(
                            onProfileClick = { navController.navigate(RouteProfile) },
                            onUpdateProfileClick = { navController.navigate(RouteUpdateProfile) },
                            onChangePasswordClick = { navController.navigate(RouteChangePassword) },
                            onNotificationsClick = { navController.navigate(RouteNotifications) },
                            onPreferencesClick = { navController.navigate(RoutePreferences) },
                            onDataManagementClick = { navController.navigate(RouteDataManagement) }
                    )
                }
                composable(RouteProfile) {
                    ProfileSettingsScreen(
                            repository = repository,
                            onBack = { navController.popBackStack() }
                    )
                }
                composable(RouteUpdateProfile) {
                    UpdateProfileSettingsScreen(
                            repository = repository,
                            currentUser = currentUser,
                            onProfileUpdated = { updated -> currentUser = updated },
                            onBack = { navController.popBackStack() }
                    )
                }
                composable(RouteChangePassword) {
                    ChangePasswordSettingsScreen(
                            repository = repository,
                            onBack = { navController.popBackStack() }
                    )
                }
                composable(RouteNotifications) {
                    NotificationSettingsScreen(onBack = { navController.popBackStack() })
                }
                composable(RoutePreferences) {
                    PreferencesSettingsScreen(onBack = { navController.popBackStack() })
                }
                composable(RouteDataManagement) {
                    DataManagementScreen(onBack = { navController.popBackStack() })
                }
            }
        }
    }
}

private fun selectedBottomRoute(route: String): String {
    return when (route) {
        RouteTransactions -> RouteTransactions
        RouteBills -> RouteBills
        RouteBudgets -> RouteBudgets
        else -> RouteHome
    }
}

@Composable
private fun AppLogo() {
    Box(
            modifier = Modifier.size(32.dp).background(Green40, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
    ) {
        Icon(
                Icons.Default.Savings,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun HomeScreen(currentUser: ProfileUser?) {
    LazyColumn(
            modifier = Modifier.fillMaxSize().background(Color(0xFFE8F4EC)).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                    "Dashboard",
                    color = Color(0xFF145A38),
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold
            )
            Text(
                    "Welcome back ${currentUser?.fullName?.ifBlank { "" } ?: ""}! Here's your financial overview.",
                    color = Color(0xFF226948),
                    fontSize = 25.sp
            )
        }

        item {
            Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
            ) {
                StatCard(
                        "Balance",
                        "$4690.00",
                        "Current available",
                        Green40,
                        modifier = Modifier.weight(1f)
                )
                StatCard(
                        "Income",
                        "$5000.00",
                        "Total income",
                        Green40,
                        modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.fillMaxWidth()
            ) {
                StatCard(
                        "Expenses",
                        "$310.00",
                        "Total expenses",
                        Color(0xFFE53935),
                        modifier = Modifier.weight(1f)
                )
                StatCard(
                        "Unpaid Bills",
                        "2",
                        "0 due soon",
                        Color(0xFF256B46),
                        modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            CardBlock("Budget Progress") {
                Row(
                        modifier = Modifier.fillMaxWidth().height(120.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.Bottom
                ) {
                    BudgetBar("Food", 0.78f, Modifier.weight(1f))
                    BudgetBar("Transport", 0.22f, Modifier.weight(1f))
                    BudgetBar("Utilities", 0.06f, Modifier.weight(1f))
                    BudgetBar("Shopping", 0.04f, Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                    LegendDot("Spent", Color(0xFFE53935))
                    LegendDot("Remaining", Color(0xFF20A656))
                }
            }
        }

        item {
            CardBlock("Expenses by Category") {
                Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                            modifier =
                                    Modifier.size(160.dp)
                                            .background(Color(0xFF1EB980), CircleShape)
                                            .border(3.dp, Color(0xFF3A7BE0), CircleShape)
                    )
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        LegendDot("Food & Dining 77%", Color(0xFF1EB980))
                        LegendDot("Transportation", Color(0xFF3A7BE0))
                        LegendDot("Entertainment", Color(0xFF7A59E0))
                    }
                }
            }
        }

        item {
            CardBlock("Budget Limits") {
                BudgetLine("Food & Dining", "$250.00 / $800.00", 0.31f)
                BudgetLine("Transportation", "$60.00 / $400.00", 0.15f)
                BudgetLine("Utilities", "$0.00 / $300.00", 0.0f)
                BudgetLine("Entertainment", "$15.00 / $200.00", 0.08f)
                BudgetLine("Shopping", "$0.00 / $500.00", 0.0f)
            }
        }

        item {
            CardBlock("Recent Transactions") {
                TransactionRow(
                        "Gas Station",
                        "Transportation • Mar 4",
                        "-$60.00",
                        Color(0xFFE53935)
                )
                TransactionRow(
                        "Grocery Shopping",
                        "Food & Dining • Mar 2",
                        "-$250.00",
                        Color(0xFFE53935)
                )
                TransactionRow("Monthly Salary", "Salary • Mar 1", "+$5000.00", Green40)
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
private fun TransactionsScreen() {
    LazyColumn(
            modifier = Modifier.fillMaxSize().background(Color(0xFFE8F4EC)).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                    "Transactions",
                    color = Color(0xFF145A38),
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold
            )
            Text("Track all your income and expenses", color = Color(0xFF226948), fontSize = 25.sp)
        }

        item {
            Button(
                    onClick = {},
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Green40),
                    modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Transaction")
            }
        }

        item { StatWideCard("Total Income", "$5000.00", Green40) }
        item { StatWideCard("Total Expenses", "$310.00", Color(0xFFE53935)) }
        item { StatWideCard("Net Balance", "$4690.00", Green40) }

        item {
            CardBlock("") {
                OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = { Text("Search transactions...") },
                        readOnly = true,
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                        value = "All Types",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                        value = "All Categories",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = textFieldColors()
                )
            }
        }

        item {
            CardBlock("Transaction History") {
                TransactionRow(
                        "Gas Station",
                        "Transportation • CARD • Mar 4, 2026",
                        "-$60.00",
                        Color(0xFFE53935)
                )
                Divider(color = Color(0xFFD5EADC), modifier = Modifier.padding(vertical = 8.dp))
                TransactionRow(
                        "Grocery Shopping",
                        "Food & Dining • CASH • Mar 2, 2026",
                        "-$250.00",
                        Color(0xFFE53935)
                )
                Divider(color = Color(0xFFD5EADC), modifier = Modifier.padding(vertical = 8.dp))
                TransactionRow(
                        "Monthly Salary",
                        "Salary • BANK • Mar 1, 2026",
                        "+$5000.00",
                        Green40
                )
            }
        }
    }
}

@Composable
private fun BillsScreen() {
    LazyColumn(
            modifier = Modifier.fillMaxSize().background(Color(0xFFE8F4EC)).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text("Bills", color = Color(0xFF145A38), fontSize = 42.sp, fontWeight = FontWeight.Bold)
            Text(
                    "Track and manage your recurring bills",
                    color = Color(0xFF226948),
                    fontSize = 25.sp
            )
        }

        item {
            Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Green40),
                    shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Bill")
            }
        }

        item { StatWideCard("Total Bills", "3", Green40) }
        item {
            StatWideCard(
                    "Unpaid Amount",
                    "$165.00",
                    Color(0xFFE53935),
                    subtitle = "2 bill(s) pending"
            )
        }
        item {
            StatWideCard("Overdue", "0", Color(0xFFE53935), subtitle = "Need immediate attention")
        }

        item {
            CardBlock("") {
                Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Filter by Status:", color = Green40, fontWeight = FontWeight.SemiBold)
                    OutlinedTextField(
                            value = "All Bills",
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.weight(1f),
                            colors = textFieldColors()
                    )
                }
            }
        }

        item {
            BillCard(
                    "Electric Bill",
                    "$120.00",
                    "Mar 15, 2026",
                    "UNPAID",
                    "Monthly electric utility"
            )
        }
        item { BillCard("Water Bill", "$45.00", "Mar 18, 2026", "UNPAID", "Monthly water utility") }
        item {
            BillCard("Netflix Subscription", "$15.00", "Mar 10, 2026", "PAID", "Streaming service")
        }
    }
}

@Composable
private fun BudgetLimitsScreen() {
    LazyColumn(
            modifier = Modifier.fillMaxSize().background(Color(0xFFE8F4EC)).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                    "Budget Limits",
                    color = Color(0xFF145A38),
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold
            )
            Text(
                    "Set spending limits for each category",
                    color = Color(0xFF226948),
                    fontSize = 25.sp
            )
        }
        item {
            Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Green40),
                    shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Set Budget Limit")
            }
        }

        item {
            CardBlock("") {
                Text(
                        "Active Period:",
                        color = Green40,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                )
                Text("3/1/2026 - 3/31/2026", color = Green40.copy(alpha = 0.85f), fontSize = 22.sp)
            }
        }

        item {
            CardBlock("Overall Budget") {
                Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("Total Budget", color = Green40.copy(alpha = 0.8f))
                        Text(
                                "$0.00",
                                color = Green40,
                                fontSize = 42.sp,
                                fontWeight = FontWeight.Bold
                        )
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text("Total Spent", color = Green40.copy(alpha = 0.8f))
                        Text(
                                "$0.00",
                                color = Green40,
                                fontSize = 42.sp,
                                fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Progress", color = Green40)
                Spacer(modifier = Modifier.height(6.dp))
                ProgressTrack(0f)
            }
        }

        item {
            CardBlock("") {
                Text(
                        "No budget limits set yet",
                        color = Green40,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                        "Click \"Set Budget Limit\" to create your first budget",
                        color = Green40.copy(alpha = 0.85f),
                        fontSize = 24.sp
                )
            }
        }
    }
}

@Composable
private fun ReportsScreen() {
    LazyColumn(
            modifier = Modifier.fillMaxSize().background(Color(0xFFE8F4EC)).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                    "Financial Reports",
                    color = Color(0xFF145A38),
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold
            )
            Text(
                    "Analyze your spending and income patterns",
                    color = Color(0xFF226948),
                    fontSize = 25.sp
            )
        }

        item {
            OutlinedTextField(
                    value = "Last 30 Days",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors()
            )
        }

        item { StatWideCard("Total Income", "$5000.00", Green40, subtitle = "1 transactions") }
        item {
            StatWideCard(
                    "Total Expenses",
                    "$310.00",
                    Color(0xFFE53935),
                    subtitle = "2 transactions"
            )
        }
        item { StatWideCard("Net Savings", "$4690.00", Green40, subtitle = "Positive balance") }
        item { StatWideCard("Savings Rate", "93.8%", Green40, subtitle = "Of total income") }

        item {
            CardBlock("Income vs Expenses Trend") {
                Box(
                        modifier =
                                Modifier.fillMaxWidth()
                                        .height(170.dp)
                                        .border(1.dp, Color(0xFFD7ECDC), RoundedCornerShape(8.dp))
                                        .padding(12.dp)
                ) {
                    Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                    ) {
                        kotlin.repeat(20) { index: Int ->
                            val h = if (index == 15) 115.dp else 6.dp
                            Box(
                                    modifier =
                                            Modifier.width(6.dp)
                                                    .height(h)
                                                    .background(Color(0xFFE53935), CircleShape)
                            )
                        }
                    }
                }
            }
        }

        item {
            CardBlock("Expenses by Category") {
                Box(
                        modifier =
                                Modifier.size(190.dp)
                                        .background(Color(0xFF1EB980), CircleShape)
                                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        item {
            CardBlock("Payment Methods") {
                Box(
                        modifier =
                                Modifier.size(190.dp)
                                        .background(Color(0xFF52D17F), CircleShape)
                                        .align(Alignment.CenterHorizontally)
                )
            }
        }

        item {
            CardBlock("Top Spending Categories") {
                BudgetLine("Food & Dining", "$250.00", 0.81f)
                Text(
                        "80.6% of total expenses",
                        color = Green40.copy(alpha = 0.8f),
                        fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(10.dp))
                BudgetLine("Transportation", "$60.00", 0.19f)
                Text(
                        "19.4% of total expenses",
                        color = Green40.copy(alpha = 0.8f),
                        fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun CategoriesScreen() {
    val incomeCategories = listOf("Salary")
    val expenseCategories =
            listOf(
                    "Food & Dining",
                    "Transportation",
                    "Utilities",
                    "Entertainment",
                    "Shopping",
                    "Healthcare"
            )

    LazyColumn(
            modifier = Modifier.fillMaxSize().background(Color(0xFFE8F4EC)).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                    "Categories",
                    color = Color(0xFF145A38),
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold
            )
            Text("Organize your income and expenses", color = Color(0xFF226948), fontSize = 25.sp)
        }
        item {
            Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Green40),
                    shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Add Category")
            }
        }

        item { StatWideCard("Income Categories", "1", Green40) }
        item { StatWideCard("Expense Categories", "6", Green40) }

        item {
            Text(
                    "Income Categories",
                    color = Green40,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
            )
        }
        items(incomeCategories) { title -> CategoryCard(title = title, type = "Income") }

        item {
            Text(
                    "Expense Categories",
                    color = Green40,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
            )
        }
        items(expenseCategories) { title -> CategoryCard(title = title, type = "Expense") }
    }
}

@Composable
private fun SettingsScreen(
        onProfileClick: () -> Unit,
        onUpdateProfileClick: () -> Unit,
        onChangePasswordClick: () -> Unit,
        onNotificationsClick: () -> Unit,
        onPreferencesClick: () -> Unit,
        onDataManagementClick: () -> Unit
) {
    LazyColumn(
            modifier = Modifier.fillMaxSize().background(Color(0xFFE8F4EC)).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Text(
                    "Settings",
                    color = Color(0xFF145A38),
                    fontSize = 42.sp,
                    fontWeight = FontWeight.Bold
            )
            Text("Manage your account and preferences", color = Color(0xFF226948), fontSize = 25.sp)
        }

        item {
            SettingsMenuCard(
                    "Profile",
                    "View your information",
                    Icons.Default.Person,
                    onProfileClick
            )
        }
        item {
            SettingsMenuCard(
                    "Update Profile",
                    "Edit name and email",
                    Icons.Default.Edit,
                    onUpdateProfileClick
            )
        }
        item {
            SettingsMenuCard(
                    "Security",
                    "Change your password",
                    Icons.Default.Lock,
                    onChangePasswordClick
            )
        }
        item {
            SettingsMenuCard(
                    "Notifications",
                    "Manage your alerts",
                    Icons.Default.Notifications,
                    onNotificationsClick
            )
        }
        item {
            SettingsMenuCard(
                    "Preferences",
                    "Customize your experience",
                    Icons.Default.Tune,
                    onPreferencesClick
            )
        }
        item {
            SettingsMenuCard(
                    "Data Management",
                    "Export or clear your data",
                    Icons.Default.DeleteOutline,
                    onDataManagementClick
            )
        }
    }
}

@Composable
private fun ProfileSettingsScreen(repository: BudgetikoRepository, onBack: () -> Unit) {
    var user by remember { mutableStateOf<ProfileUser?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        loading = true
        when (val result = repository.getProfile()) {
            is ApiResult.Success -> {
                user = result.data.user
                errorMessage = null
            }
            is ApiResult.Error -> {
                errorMessage = result.message
            }
        }
        loading = false
    }

    ScreenSettingsContainer("Profile", "Update your personal information", onBack) {
        if (loading) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Green40)
            Spacer(modifier = Modifier.height(8.dp))
        }

        Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF7FCF9)),
                shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                            modifier =
                                    Modifier.size(76.dp).background(Color(0xFFE1EFE7), CircleShape),
                            contentAlignment = Alignment.Center
                    ) {
                        Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                tint = Green40,
                                modifier = Modifier.size(38.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                                user?.fullName?.ifBlank { "No name set" } ?: "No name set",
                                color = Green40,
                                fontWeight = FontWeight.Bold,
                                fontSize = 26.sp
                        )
                        Text(
                                user?.email?.ifBlank { "No email" } ?: "No email",
                                color = Green40.copy(alpha = 0.85f),
                                fontSize = 20.sp
                        )
                    }
                }
            }
        }

        if (errorMessage != null) {
            Text(errorMessage ?: "", color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
        }
    }
}

@Composable
private fun UpdateProfileSettingsScreen(
        repository: BudgetikoRepository,
        currentUser: ProfileUser?,
        onProfileUpdated: (ProfileUser) -> Unit,
        onBack: () -> Unit
) {
    var fullName by remember { mutableStateOf(currentUser?.fullName.orEmpty()) }
    var email by remember { mutableStateOf(currentUser?.email.orEmpty()) }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    ScreenSettingsContainer("Profile", "Update your personal information", onBack) {
        OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("Full Name") },
                enabled = !loading,
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
        )
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                enabled = !loading,
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
                enabled = !loading,
                onClick = {
                    if (fullName.isBlank() || email.isBlank()) {
                        errorMessage = "Full name and email are required."
                        return@Button
                    }

                    coroutineScope.launch {
                        loading = true
                        errorMessage = null
                        successMessage = null

                        when (val result = repository.updateProfile(fullName.trim(), email.trim())
                        ) {
                            is ApiResult.Success -> {
                                val updated = result.data.user
                                if (updated != null) {
                                    onProfileUpdated(updated)
                                }
                                successMessage =
                                        result.data.message ?: "Profile updated successfully."
                            }
                            is ApiResult.Error -> {
                                errorMessage = result.message
                            }
                        }

                        loading = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Green40)
        ) {
            if (loading) {
                CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Save Changes")
            }
        }

        if (errorMessage != null) {
            Text(errorMessage ?: "", color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
        }
        if (successMessage != null) {
            Text(successMessage ?: "", color = Green40, fontSize = 14.sp)
        }
    }
}

@Composable
private fun ChangePasswordSettingsScreen(repository: BudgetikoRepository, onBack: () -> Unit) {
    var current by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirm by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    ScreenSettingsContainer("Security", "Change your password", onBack) {
        Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8EA)),
                shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                    "This is a demo app. Passwords are not encrypted in production environments.",
                    color = Color(0xFFC86A00),
                    modifier = Modifier.padding(12.dp)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        PasswordField(
                current,
                { current = it },
                "Current Password",
                showPassword,
                { showPassword = !showPassword },
                enabled = !loading
        )
        Spacer(modifier = Modifier.height(10.dp))
        PasswordField(
                newPassword,
                { newPassword = it },
                "New Password",
                showPassword,
                { showPassword = !showPassword },
                enabled = !loading
        )
        Spacer(modifier = Modifier.height(10.dp))
        PasswordField(
                confirm,
                { confirm = it },
                "Confirm New Password",
                showPassword,
                { showPassword = !showPassword },
                enabled = !loading
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
                enabled = !loading,
                onClick = {
                    if (current.isBlank() || newPassword.isBlank() || confirm.isBlank()) {
                        errorMessage = "All password fields are required."
                        return@Button
                    }

                    coroutineScope.launch {
                        loading = true
                        errorMessage = null
                        successMessage = null

                        when (val result = repository.changePassword(current, newPassword, confirm)
                        ) {
                            is ApiResult.Success -> {
                                successMessage =
                                        result.data.message ?: "Password changed successfully."
                                current = ""
                                newPassword = ""
                                confirm = ""
                            }
                            is ApiResult.Error -> {
                                errorMessage = result.message
                            }
                        }

                        loading = false
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Green40)
        ) {
            if (loading) {
                CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Update Password")
            }
        }

        if (errorMessage != null) {
            Text(errorMessage ?: "", color = MaterialTheme.colorScheme.error, fontSize = 14.sp)
        }
        if (successMessage != null) {
            Text(successMessage ?: "", color = Green40, fontSize = 14.sp)
        }
    }
}

@Composable
private fun NotificationSettingsScreen(onBack: () -> Unit) {
    var all by remember { mutableStateOf(true) }
    var reminders by remember { mutableStateOf(true) }
    var alerts by remember { mutableStateOf(true) }

    ScreenSettingsContainer("Notifications", "Manage your alerts", onBack) {
        ToggleCard("Enable Notifications", "Receive all app notifications", all) { all = it }
        Spacer(modifier = Modifier.height(10.dp))
        ToggleCard("Bill Reminders", "Get notified about upcoming bills", reminders) {
            reminders = it
        }
        Spacer(modifier = Modifier.height(10.dp))
        ToggleCard("Budget Alerts", "Alerts when approaching limits", alerts) { alerts = it }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Green40)
        ) { Text("Save Settings") }
    }
}

@Composable
private fun PreferencesSettingsScreen(onBack: () -> Unit) {
    var currency by remember { mutableStateOf("") }

    ScreenSettingsContainer("Preferences", "Customize your experience", onBack) {
        OutlinedTextField(
                value = currency,
                onValueChange = { currency = it },
                label = { Text("Default Currency") },
                leadingIcon = { Text("$") },
                modifier = Modifier.fillMaxWidth(),
                colors = textFieldColors()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
                "Used for displaying amounts throughout the app",
                color = Green40.copy(alpha = 0.8f),
                fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Green40)
        ) { Text("Save Preferences") }
    }
}

@Composable
private fun DataManagementScreen(onBack: () -> Unit) {
    ScreenSettingsContainer("Data Management", "Export or clear your data", onBack) {
        Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEEF4FF)),
                shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                        "Export Data",
                        color = Color(0xFF1E4DA1),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                )
                Text("Download all financial data as JSON backup", color = Color(0xFF245CC0))
                OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.Download, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Export All Data")
                }
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF1F1)),
                shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                    modifier = Modifier.padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                        "Clear All Data",
                        color = Color(0xFFB3261E),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                )
                Text("Permanently delete all data. Cannot be undone!", color = Color(0xFFB3261E))
                OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                    Icon(
                            Icons.Default.DeleteOutline,
                            contentDescription = null,
                            tint = Color(0xFFB3261E)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Clear All Data", color = Color(0xFFB3261E))
                }
            }
        }
    }
}

@Composable
private fun ScreenSettingsContainer(
        title: String,
        subtitle: String,
        onBack: () -> Unit,
        content: @Composable ColumnScope.() -> Unit
) {
    LazyColumn(
            modifier = Modifier.fillMaxSize().background(Color(0xFFE8F4EC)).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable(onClick = onBack)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Green40)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                        "Settings",
                        color = Green40,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 28.sp
                )
            }
        }

        item {
            Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors =
                            CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                            )
            ) {
                Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        content = content
                )
            }
        }
    }
}

@Composable
private fun PasswordField(
        value: String,
        onValueChange: (String) -> Unit,
        label: String,
        showPassword: Boolean,
        onToggle: () -> Unit,
        enabled: Boolean = true
) {
    OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            enabled = enabled,
            visualTransformation =
                    if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = onToggle) {
                    Icon(
                            if (showPassword) Icons.Default.VisibilityOff
                            else Icons.Default.Visibility,
                            contentDescription = "Toggle"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = textFieldColors()
    )
}

@Composable
private fun ToggleCard(
        title: String,
        subtitle: String,
        checked: Boolean,
        onChecked: (Boolean) -> Unit
) {
    Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF6EF)),
            shape = RoundedCornerShape(12.dp)
    ) {
        Row(
                modifier = Modifier.fillMaxWidth().padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(title, color = Green40, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(subtitle, color = Green40.copy(alpha = 0.82f), fontSize = 15.sp)
            }
            Switch(checked = checked, onCheckedChange = onChecked)
        }
    }
}

@Composable
private fun SettingsMenuCard(
        title: String,
        subtitle: String,
        icon: ImageVector,
        onClick: () -> Unit
) {
    Card(
            modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = title, tint = Green40)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(title, color = Green40, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(subtitle, color = Green40.copy(alpha = 0.8f), fontSize = 14.sp)
            }
        }
    }
}

@Composable
private fun CategoryCard(title: String, type: String) {
    Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
                modifier = Modifier.fillMaxWidth().padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                        modifier =
                                Modifier.size(44.dp)
                                        .background(
                                                if (type == "Income") Color(0xFF1B8B4A)
                                                else Color(0xFF1EB980),
                                                RoundedCornerShape(10.dp)
                                        ),
                        contentAlignment = Alignment.Center
                ) { Icon(Icons.Default.Category, contentDescription = null, tint = Color.White) }
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(title, color = Green40, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                    Text(type, color = Green40.copy(alpha = 0.85f), fontSize = 14.sp)
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Green40)
                Icon(
                        Icons.Default.DeleteOutline,
                        contentDescription = "Delete",
                        tint = Color(0xFFE53935)
                )
            }
        }
    }
}

@Composable
private fun BillCard(title: String, amount: String, date: String, status: String, note: String) {
    Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(title, color = Green40, fontWeight = FontWeight.Bold, fontSize = 30.sp)
                Box(
                        modifier =
                                Modifier.background(
                                                color =
                                                        if (status == "PAID") Color(0xFFE5F4E9)
                                                        else Color(0xFFF1F8F2),
                                                shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) { Text(status, color = Green40, fontSize = 14.sp) }
            }
            Text("Utilities", color = Green40.copy(alpha = 0.85f), fontSize = 22.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(amount, color = Green40, fontWeight = FontWeight.Bold, fontSize = 30.sp)
                Text(date, color = Green40.copy(alpha = 0.85f), fontSize = 28.sp)
            }
            Text("Recurring monthly", color = Green40.copy(alpha = 0.9f), fontSize = 18.sp)
            Text(note, color = Green40.copy(alpha = 0.85f), fontSize = 18.sp)
            Icon(
                    Icons.Default.DeleteOutline,
                    contentDescription = "Delete",
                    tint = Color(0xFFE53935)
            )
        }
    }
}

@Composable
private fun StatWideCard(title: String, amount: String, amountColor: Color, subtitle: String = "") {
    Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(title, color = Green40, fontSize = 26.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(amount, color = amountColor, fontSize = 44.sp, fontWeight = FontWeight.Bold)
            if (subtitle.isNotBlank()) {
                Text(subtitle, color = Green40.copy(alpha = 0.82f), fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun StatCard(
        title: String,
        amount: String,
        subtitle: String,
        amountColor: Color,
        modifier: Modifier = Modifier
) {
    Card(
            modifier = modifier,
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
                modifier = Modifier.padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(title, color = Green40, fontSize = 19.sp)
            Text(amount, color = amountColor, fontSize = 36.sp, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Green40.copy(alpha = 0.8f), fontSize = 14.sp)
        }
    }
}

@Composable
private fun CardBlock(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (title.isNotBlank()) {
                Text(title, color = Green40, fontWeight = FontWeight.Bold, fontSize = 32.sp)
            }
            content()
        }
    }
}

@Composable
private fun BudgetBar(label: String, ratio: Float, modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
        Box(
                modifier =
                        Modifier.width(16.dp)
                                .height(100.dp * ratio.coerceIn(0f, 1f))
                                .background(Color(0xFF20A656), RoundedCornerShape(5.dp))
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(label, color = Green40.copy(alpha = 0.75f), fontSize = 11.sp)
    }
}

@Composable
private fun LegendDot(label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(10.dp).background(color, CircleShape))
        Spacer(modifier = Modifier.width(6.dp))
        Text(label, color = Green40.copy(alpha = 0.85f), fontSize = 12.sp)
    }
}

@Composable
private fun BudgetLine(title: String, value: String, progress: Float) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(title, color = Green40, fontWeight = FontWeight.SemiBold)
        Text(value, color = Green40)
    }
    Spacer(modifier = Modifier.height(5.dp))
    ProgressTrack(progress)
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun ProgressTrack(progress: Float) {
    Box(
            modifier =
                    Modifier.fillMaxWidth()
                            .height(8.dp)
                            .background(Color(0xFFD2EADB), RoundedCornerShape(6.dp))
    ) {
        Box(
                modifier =
                        Modifier.fillMaxWidth(progress.coerceIn(0f, 1f))
                                .height(8.dp)
                                .background(Color(0xFF1DA255), RoundedCornerShape(6.dp))
        )
    }
}

@Composable
private fun TransactionRow(title: String, subtitle: String, amount: String, amountColor: Color) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(
                modifier =
                        Modifier.size(36.dp)
                                .background(Color(0xFFDDF0E4), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
        ) {
            Icon(
                    Icons.Default.PieChart,
                    contentDescription = null,
                    tint = Green40,
                    modifier = Modifier.size(18.dp)
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = Green40, fontWeight = FontWeight.SemiBold)
            Text(subtitle, color = Green40.copy(alpha = 0.8f), fontSize = 12.sp)
        }
        Text(amount, color = amountColor, fontWeight = FontWeight.Bold)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun textFieldColors() =
        TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFB3DDC3),
                unfocusedBorderColor = Color(0xFFB3DDC3),
                focusedTextColor = Green40,
                unfocusedTextColor = Green40,
                cursorColor = Green40
        )
