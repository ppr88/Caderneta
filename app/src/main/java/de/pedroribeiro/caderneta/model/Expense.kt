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
                   @ColumnInfo(name = "cat_id")         var category_id: Int = Category.CATEGORY_ID_OTHER,
                   @ColumnInfo(name = "description")    var description: String? = null,
                   @ColumnInfo(name = "spent_at")       var spendDate: Date = Calendar.getInstance().time) {

    companion object {
        val samples: List<Expense> = listOf(
                Expense(null, 20.00, Category.CATEGORY_ID_BILLS,"Hairdresser"),
                Expense(null, 14.32, Category.CATEGORY_ID_SUPERMARKET,"Edeka"),
                Expense(null, 35.00, Category.CATEGORY_ID_LEISURE,"Irish pub"),
                Expense(null, 5.00, Category.CATEGORY_ID_OTHER,"Pizza"),
                Expense(null, 39.99, Category.CATEGORY_ID_BILLS,"UnityMedia")
        )
    }
}