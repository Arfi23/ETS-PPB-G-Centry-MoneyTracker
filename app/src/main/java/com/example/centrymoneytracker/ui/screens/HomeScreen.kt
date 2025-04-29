package com.example.centrymoneytracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.centrymoneytracker.PoppinsFont
import com.example.centrymoneytracker.R
import com.example.centrymoneytracker.model.Transaction
import com.example.centrymoneytracker.ui.components.TransactionGroupCard
import com.example.centrymoneytracker.viewmodel.TransactionViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PieChart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: TransactionViewModel = viewModel(),
    onTransactionClick: (Transaction) -> Unit = {}
) {
    val transactions by viewModel.transactions.collectAsState()

    val transactionsByDate = transactions.groupBy { it.date }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Centry Money Tracker", fontFamily = PoppinsFont) },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.logo_icon),
                        contentDescription = "Logo Icon",
                        modifier = Modifier.size(80.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            Column {
                FloatingActionButton(
                    onClick = { navController.navigate("analytics") },
                    containerColor = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.piechart),
                        contentDescription = "Analytics",
                        modifier = Modifier.size(20.dp)
                    )
                }

                FloatingActionButton(
                    onClick = { navController.navigate("add_transaction") },
                    containerColor = MaterialTheme.colorScheme.primary,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.plus),
                        contentDescription = "Add Data Icon",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

        }
    ) { padding ->
        if (transactionsByDate.isEmpty()) {
            // Empty State
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Gambar ilustrasi
                    Image(
                        painter = painterResource(id = R.drawable.notransaction),
                        contentDescription = "No transactions illustration",
                        modifier = Modifier
                            .size(200.dp) // ukuran gambar
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Teks
                    Text(
                        text = "Belum ada transaksi",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontFamily = PoppinsFont
                    )
                }
            }
        } else {
            LazyColumn(
//                contentPadding = padding,
                contentPadding = PaddingValues(
                    start = 8.dp,
                    top = padding.calculateTopPadding() + 8.dp,
                    end = 8.dp,
                    bottom = padding.calculateBottomPadding() + 80.dp // jarak dengan FAB
                ),
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
}
