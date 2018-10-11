package de.pedroribeiro.caderneta.model

import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ColumnInfo
import android.support.annotation.NonNull

import java.util.*


@Entity
data class Expense(@PrimaryKey(autoGenerate = true)     var id: Long?,
                   @NonNull @ColumnInfo(name = "value") var value: Double,
                   @ColumnInfo(name = "category")       var category: Category = Category.Other,
                   @ColumnInfo(name = "name")           var name: String? = null,
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