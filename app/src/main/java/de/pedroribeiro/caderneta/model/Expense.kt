package de.pedroribeiro.caderneta.model

import androidx.annotation.NonNull
import androidx.room.*
import androidx.room.ForeignKey.CASCADE

import java.util.*


@Entity(foreignKeys = [
                ForeignKey(entity = Category::class,
                            parentColumns = ["category_id"],
                            childColumns = ["cat_id"])
        ],
        indices = [Index("cat_id")])
data class Expense(@PrimaryKey(autoGenerate = true)     var id: Long?,
                   @NonNull @ColumnInfo(name = "value") var value: Double,
                   @ColumnInfo(name = "cat_id")         var category_id: Int = 8,
                   @ColumnInfo(name = "description")    var description: String? = null,
                   @ColumnInfo(name = "spent_at")       var spendDate: Date = Calendar.getInstance().time) {

    companion object {
        val samples: List<Expense> = listOf(
                Expense(null, 20.00, 2,"Hairdresser"),
                Expense(null, 14.32, 1,"Edeka"),
                Expense(null, 35.00, 6,"Irish pub"),
                Expense(null, 5.00, 0,"Pizza"),
                Expense(null, 39.99, 2,"UnityMedia")
        )
    }
}