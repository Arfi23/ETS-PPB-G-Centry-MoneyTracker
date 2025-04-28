package com.example.centrymoneytracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.centrymoneytracker.viewmodel.TransactionViewModel
import com.example.centrymoneytracker.ui.components.TransactionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: TransactionViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Centry") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_transaction") }
            ) {
                Text("+")
            }
        }
    ) { innerPadding ->
        val transactions = viewModel.transactions.collectAsState().value

        LazyColumn(
            contentPadding = innerPadding,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(transactions) { transaction ->
                TransactionItem(
                    transaction = transaction,
                    onClick = { /* Nanti isi kalau mau buat klik transaction */ }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
