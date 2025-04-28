package com.example.centrymoneytracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.centrymoneytracker.model.Transaction
import com.example.centrymoneytracker.ui.components.TransactionGroupCard
import com.example.centrymoneytracker.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeoScreen(
    navController: NavController,
    viewModel: TransactionViewModel = viewModel(),
    onTransactionClick: (Transaction) -> Unit = {}
) {
    val transactions by viewModel.transactions.collectAsState()

    val transactionsByDate = transactions.groupBy { it.date }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Centry Money Tracker") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_transaction") }
            ) {
                Text("+")
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(transactionsByDate.keys.sortedDescending()) { date ->
                val transactionsForDate = transactionsByDate[date] ?: emptyList()

                TransactionGroupCard(
                    date = date,
                    transactions = transactionsForDate,
                    onClick = onTransactionClick
                )
            }
        }
    }
}
