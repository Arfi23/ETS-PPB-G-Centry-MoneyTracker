package com.example.centrymoneytracker.viewmodel

import androidx.lifecycle.ViewModel
import com.example.centrymoneytracker.model.Transaction
import com.example.centrymoneytracker.model.TransactionType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TransactionViewModel : ViewModel() {
    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions = _transactions.asStateFlow()

    private var nextId = 1

//    fun addTransaction(type: String, category: String, amount: Double, date: String) {
//        val newTransaction = Transaction(
//            nextId++,
//            type = type,
//            category = category,
//            amount = amount,
//            date = date
//        )
//        _transactions.value = _transactions.value + newTransaction
//    }
    fun addTransaction(type: TransactionType, category: String, amount: Double, date: String) {
        val newTransaction = Transaction(
            nextId++,
            type = type,
            category = category,
            amount = amount,
            date = date
        )
        _transactions.value = _transactions.value + newTransaction
    }

    fun deleteTransaction(transaction: Transaction) {
        _transactions.value = _transactions.value - transaction
    }

    fun updateTransaction(updatedTransaction: Transaction) {
        _transactions.value = _transactions.value.map {
            if (it.id == updatedTransaction.id) updatedTransaction else it
        }
    }
}
