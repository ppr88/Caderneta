package de.pedroribeiro.caderneta.model

import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.annotation.NonNull

import java.util.*


@Entity
data class Expense(@PrimaryKey(autoGenerate = true)     var id: Long?,
                   @NonNull @ColumnInfo(name = "value") var value: Double,
                   @ColumnInfo(name = "category")       var category: Category = Category.Other,
                   @ColumnInfo(name = "description")    var description: String? = null,
                   @ColumnInfo(name = "spent_at")       var spendDate: Date = Calendar.getInstance().time) {

    companion object {
        val samples: List<Expense> = listOf<Expense>(
                Expense(null, 20.00, Category.Bills,"Hairdresser"),
                Expense(null, 14.32, Category.Supermarket,"Edeka"),
                Expense(null, 35.00, Category.Leisure,"Irish pub"),
                Expense(null, 5.00, Category.Food,"Pizza"),
                Expense(null, 39.99, Category.Bills,"UnityMedia")
        )
    }

    enum class Category() {
        Food,
        Supermarket,
        Bills,
        Shopping,
        Travel,
        Rent,
        Transportation,
        Leisure,
        Other
    }
}