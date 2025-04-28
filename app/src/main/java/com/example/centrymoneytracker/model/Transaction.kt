package com.example.centrymoneytracker.model

data class Transaction(
    val id: Int,
    val type: TransactionType, // "Income" atau "Expense"
    val category: String,
    val amount: Double,
    val date: String
)