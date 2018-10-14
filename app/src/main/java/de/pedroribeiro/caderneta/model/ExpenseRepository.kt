package de.pedroribeiro.caderneta.model

import androidx.lifecycle.LiveData
import android.app.Application
import android.os.AsyncTask

class ExpenseRepository(application: Application) {

    private val expenseDao: ExpenseDao = AppRoomDatabase.getInstance(application).expenseDao()
    private val allExpenses: LiveData<List<Expense>> = expenseDao.getAllExpenses()

    fun getAllExpenses(): LiveData<List<Expense>> {
        return allExpenses
    }

    fun insert(expense: Expense) {
        InsertAsyncTask(expenseDao).execute(expense)
    }

    private class InsertAsyncTask(private val dao: ExpenseDao) : AsyncTask<Expense, Unit, Unit>() {

        override fun doInBackground(vararg params: Expense): Unit {
            dao.insert(params[0])
            return
        }
    }
}


