package com.example.centrymoneytracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.centrymoneytracker.viewmodel.TransactionViewModel
import com.example.centrymoneytracker.model.TransactionType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import java.util.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(navController: NavController, viewModel: TransactionViewModel) {
    val context = LocalContext.current
    var selectedType by remember { mutableStateOf(TransactionType.INCOME) }
    var category by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())) }
    val calendar = Calendar.getInstance()

    // State untuk dropdown kategori
    var expanded by remember { mutableStateOf(false) }

    // List kategori berdasarkan jenis transaksi
    val incomeCategories = listOf("Kiriman Keluarga", "Gaji", "Investasi")
    val expenseCategories = listOf("Makanan", "Belanja", "Transportasi")

    // Untuk Pemilihan Kelompok Isi Kategori
    val categories = if (selectedType == TransactionType.INCOME) incomeCategories else expenseCategories

    val isFormValid = category.isNotBlank() && amount.toDoubleOrNull() != null && amount.toDoubleOrNull()!! > 0

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Transaction") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Pilih jenis transaksi
            Text(text = "Transaction Type", style = MaterialTheme.typography.titleMedium)
            Row {
                RadioButton(
                    selected = selectedType == TransactionType.INCOME,
                    onClick = {
                        selectedType = TransactionType.INCOME
                        category = "" //reset category saat ganti tipe transaksi
                    }
                )
                Text("Income", modifier = Modifier.padding(start = 8.dp))

                Spacer(modifier = Modifier.width(16.dp))

                RadioButton(
                    selected = selectedType == TransactionType.EXPENSE,
                    onClick = {
                        selectedType = TransactionType.EXPENSE
                        category = "" //reset category saat ganti tipe transaksi
                    }
                )
                Text("Expense", modifier = Modifier.padding(start = 8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dropdown Kategori
            Text(text = "Category", style = MaterialTheme.typography.titleMedium)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = category,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Select Category") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                category = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Input nominal
            OutlinedTextField(
                value = amount,
                onValueChange = { input ->
                    if (input.all { it.isDigit() || it == '.' }) {
                        amount = input
                    }
                },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input tanggal
            OutlinedTextField(
                value = date,
                onValueChange = {},
                label = { Text("Date") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = {
                        val datePickerDialog = DatePickerDialog(
                            context,
                            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                                calendar.set(year, month, dayOfMonth)
                                date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(calendar.time)
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        )
                        datePickerDialog.show()
                    }) {
                        Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Select Date")
                    }
                }
            )


            Spacer(modifier = Modifier.height(24.dp))

            // Tombol Simpan
            Button(
                onClick = {
                    val amountValue = amount.toDoubleOrNull() ?: 0.0
//                    viewModel.addTransaction(selectedType.name, category, amountValue, date)
                    viewModel.addTransaction(selectedType, category, amountValue, date)
                    navController.popBackStack() // Balik ke Home setelah simpan
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = isFormValid // tombol hanya aktif kalau form valid
            ) {
                Text("Save")
            }
        }
    }
}