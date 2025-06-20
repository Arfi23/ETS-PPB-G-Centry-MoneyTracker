package com.example.centrymoneytracker.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.centrymoneytracker.model.Transaction
import com.example.centrymoneytracker.model.TransactionType
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TransactionItem(transaction: Transaction, onClick: () -> Unit) {
    // Card untuk membungkus item transaksi
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = transaction.category,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            val formattedAmount = NumberFormat.getNumberInstance(Locale.getDefault()).format(transaction.amount)

            Text(
                text = "$formattedAmount IDR",
                style = MaterialTheme.typography.bodyMedium,
                color = if (transaction.type == TransactionType.INCOME) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = transaction.date,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
    }
}
