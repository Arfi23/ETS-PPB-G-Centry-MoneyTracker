// AnalyticsScreen.kt

package com.example.centrymoneytracker.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.centrymoneytracker.viewmodel.TransactionViewModel
import com.example.centrymoneytracker.model.TransactionType
//import com.smarttoolfactory.chart.pie.PieChart
//import com.smarttoolfactory.chart.pie.PieChartData
import kotlin.math.absoluteValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import com.example.centrymoneytracker.PoppinsFont
import com.example.centrymoneytracker.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnalyticsScreen(
    navController: NavController,
    viewModel: TransactionViewModel = viewModel()
) {
    val transactions by viewModel.transactions.collectAsState()

    val expenseByCategory = transactions.filter { it.type == TransactionType.EXPENSE }
        .groupBy { it.category }
        .mapValues { it.value.sumOf { transaction -> transaction.amount } }

    val incomeByCategory = transactions.filter { it.type == TransactionType.INCOME }
        .groupBy { it.category }
        .mapValues { it.value.sumOf { transaction -> transaction.amount } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Analisis Transaksi", fontFamily = com.example.centrymoneytracker.PoppinsFont) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(text = "Pengeluaran Berdasarkan Kategori", style = MaterialTheme.typography.titleMedium)
//            PieChart(
//                pieChartData = PieChartData(
//                    slices = expenseByCategory.map { (category, amount) ->
//                        PieChartData.Slice(
//                            value = amount.toFloat(),
//                            color = randomColorForCategory(category),
//                            label = category
//                        )
//                    }
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(300.dp)
//            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Pemasukan Berdasarkan Kategori", style = MaterialTheme.typography.titleMedium)
//            PieChart(
//                pieChartData = PieChartData(
//                    slices = incomeByCategory.map { (category, amount) ->
//                        PieChartData.Slice(
//                            value = amount.toFloat(),
//                            color = randomColorForCategory(category),
//                            label = category
//                        )
//                    }
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(300.dp)
//            )


            // Under Construction Box
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
                        painter = painterResource(id = R.drawable.undercons),
                        contentDescription = "No transactions illustration",
                        modifier = Modifier
                            .size(200.dp) // ukuran gambar
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Teks
                    Text(
                        text = "Mohon maaf \n masih dalam tahap pengembangan",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        fontFamily = PoppinsFont,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

// Fungsi bantu buat kasih warna random untuk tiap kategori
@Composable
fun randomColorForCategory(category: String): androidx.compose.ui.graphics.Color {
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.error,
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.secondaryContainer
    )
    val index = category.hashCode().absoluteValue % colors.size
    return colors[index]
}
