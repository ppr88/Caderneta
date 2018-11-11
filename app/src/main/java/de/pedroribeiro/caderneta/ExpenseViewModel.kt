package de.pedroribeiro.caderneta

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import de.pedroribeiro.caderneta.model.Expense
import de.pedroribeiro.caderneta.model.DataRepository
import de.pedroribeiro.caderneta.model.ExpenseWithCategory

class ExpenseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: DataRepository = DataRepository(application)
    val allExpenses: LiveData<List<ExpenseWithCategory>> = repository.getAllExpensesWithCategory()

    fun insert(expense: Expense) = repository.insert(expense)
}