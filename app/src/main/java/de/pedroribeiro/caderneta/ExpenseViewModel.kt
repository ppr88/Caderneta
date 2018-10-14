package de.pedroribeiro.caderneta

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import de.pedroribeiro.caderneta.model.Expense
import de.pedroribeiro.caderneta.model.ExpenseRepository

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ExpenseRepository = ExpenseRepository(application)
    val allExpenses: LiveData<List<Expense>> = repository.getAllExpenses()

    fun insert(expense: Expense) = repository.insert(expense)
}