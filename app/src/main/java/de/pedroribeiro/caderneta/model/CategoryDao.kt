package de.pedroribeiro.caderneta.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface CategoryDao {

    @Insert(onConflict = REPLACE)
    fun insert(category: Category)

    @Insert(onConflict = REPLACE)
    fun insertAll(categories: List<Category>)

    @Query("DELETE FROM category")
    fun deleteAll()

    @Query("SELECT * FROM category")
    fun getAllCategories() : LiveData<List<Category>>
}