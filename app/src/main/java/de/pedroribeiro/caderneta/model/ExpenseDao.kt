package de.pedroribeiro.caderneta.model

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

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