package de.pedroribeiro.caderneta.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface ExpenseDao {

    @Insert(onConflict = REPLACE)
    fun insert(expense: Expense)

    @Insert(onConflict = REPLACE)
    fun insertAll(expenses: List<Expense>)

    @Query("DELETE FROM expense")
    fun deleteAll()

    @Query("SELECT * FROM expense ORDER BY spent_at ASC")
    fun getAllExpenses() : LiveData<List<Expense>>
}