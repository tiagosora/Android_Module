package com.example.watchlistapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.watchlistapp.ui.screens.AddScreen
import com.example.watchlistapp.ui.screens.ListScreen
import com.example.watchlistapp.ui.theme.WatchListAppTheme
import com.example.watchlistapp.viewmodel.WatchListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WatchListAppTheme {
                val viewModel: WatchListViewModel = viewModel()
                WatchListApp(viewModel)
            }
        }
    }
}

@Composable
fun WatchListApp(viewModel: WatchListViewModel) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "add_screen",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("add_screen") { AddScreen(viewModel) }
            composable("list_screen") { ListScreen(viewModel) }
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        NavigationBarItem(
            selected = navController.currentDestination?.route == "add_screen",
            onClick = { navController.navigate("add_screen") },
            icon = { Icon(Icons.Filled.Add, contentDescription = "Add") },
            label = { Text("Add") }
        )
        NavigationBarItem(
            selected = navController.currentDestination?.route == "list_screen",
            onClick = { navController.navigate("list_screen") },
            icon = { Icon(Icons.Filled.List, contentDescription = "List") },
            label = { Text("List") }
        )
    }
}
