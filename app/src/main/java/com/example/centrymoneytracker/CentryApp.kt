package com.example.centrymoneytracker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.centrymoneytracker.ui.screens.AddTransactionScreen
import com.example.centrymoneytracker.ui.screens.HomeScreen
import com.example.centrymoneytracker.ui.screens.AnalyticsScreen
import com.example.centrymoneytracker.viewmodel.TransactionViewModel

@Composable
fun CentryApp() {
    val navController = rememberNavController()
    val transactionViewModel = remember { TransactionViewModel() }

      NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController, transactionViewModel)
        }
        composable("add_transaction") {
            AddTransactionScreen(navController, transactionViewModel)
        }
      composable("analytics") {
          AnalyticsScreen(navController)
      }
    }
}