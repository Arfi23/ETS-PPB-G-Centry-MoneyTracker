package com.example.centrymoneytracker.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.centrymoneytracker.model.Transaction
import com.example.centrymoneytracker.model.TransactionType
import java.text.NumberFormat
import java.util.Locale
import com.example.centrymoneytracker.ui.theme.IncomeColor
import com.example.centrymoneytracker.ui.theme.ExpenseColor

@Composable
fun TransactionGroupCard(
    date: String,
    transactions: List<Transaction>,
    onClick: (Transaction) -> Unit
) {
    val totalIncome = transactions.filter { it.type == TransactionType.INCOME }.sumOf { it.amount }
    val totalExpense = transactions.filter { it.type == TransactionType.EXPENSE }.sumOf { it.amount }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Tanggal
            Text(
                text = date,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            // List transaksi
            transactions.forEach { transaction ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClick(transaction) }
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = transaction.category,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    val formattedAmount = NumberFormat.getNumberInstance(Locale.getDefault())
                        .format(transaction.amount)

                    Text(
                        text = "${if (transaction.type == TransactionType.INCOME) "+" else "-"}$formattedAmount IDR",
                        color = if (transaction.type == TransactionType.INCOME) IncomeColor else ExpenseColor,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Divider()

            Spacer(modifier = Modifier.height(8.dp))

            // Ringkasan Jumlah Transaksi Harian (berdasarkan income dan expense)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Income: ${NumberFormat.getNumberInstance(Locale.getDefault()).format(totalIncome)} IDR",
                    fontWeight = FontWeight.Bold,
                    color = IncomeColor,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Expense: ${NumberFormat.getNumberInstance(Locale.getDefault()).format(totalExpense)} IDR",
                    fontWeight = FontWeight.Bold,
                    color = ExpenseColor,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
