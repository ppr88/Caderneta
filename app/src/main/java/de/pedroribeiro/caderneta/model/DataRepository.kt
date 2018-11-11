package de.pedroribeiro.caderneta.model

import androidx.lifecycle.LiveData
import android.app.Application
import android.os.AsyncTask

class DataRepository(application: Application) {

    private val expenseDao: ExpenseDao = AppRoomDatabase.getInstance(application).expenseDao()
    private val allExpenses: LiveData<List<ExpenseWithCategory>> = expenseDao.getAllExpensesWithCategory()

    private val categoryDao: CategoryDao = AppRoomDatabase.getInstance(application).categoryDao()
    private val allCategories: LiveData<List<Category>> = categoryDao.getAllCategories()

    fun getAllExpensesWithCategory(): LiveData<List<ExpenseWithCategory>> {
        return allExpenses
    }

    fun getAllCategories(): LiveData<List<Category>> {
        return allCategories
    }

    fun insert(expense: Expense) {
        InsertAsyncTask(expenseDao).execute(expense)
    }

    fun insert(category: Category) {
        //TODO: make InsertAsyncTask generic and add support to create new categories
        //InsertAsyncTask(categoryDao).execute(categories)
    }

    private class InsertAsyncTask(private val dao: ExpenseDao) : AsyncTask<Expense, Unit, Unit>() {

        override fun doInBackground(vararg params: Expense) {
            dao.insert(params[0])
            return
        }
    }
}


