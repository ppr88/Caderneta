package de.pedroribeiro.caderneta.model

import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.annotation.NonNull

import java.util.*


@Entity
data class Category(@PrimaryKey @ColumnInfo(name = "category_id")   var id: Long,
                    @NonNull @ColumnInfo(name = "name")             var name: String,
                    @NonNull @ColumnInfo(name = "label_id")         var labelId: String,
                    @ColumnInfo(name = "icon_id")                   var iconId: String? = null) {

    companion object {
        const val CATEGORY_ID_FOOD: Long = 0
        const val CATEGORY_ID_SUPERMARKET: Long = 1
        const val CATEGORY_ID_BILLS: Long = 2
        const val CATEGORY_ID_SHOPPING: Long = 3
        const val CATEGORY_ID_TRAVEL: Long = 4
        const val CATEGORY_ID_TRANSPORTATION: Long = 5
        const val CATEGORY_ID_LEISURE: Long = 6
        const val CATEGORY_ID_RENT: Long = 7
        const val CATEGORY_ID_OTHER: Long = 8

        val defaultCategories: List<Category> = listOf(
                Category(CATEGORY_ID_FOOD, "Food", "t_category_food","ic_category_food"),
                Category(CATEGORY_ID_SUPERMARKET, "Supermarket", "t_category_supermarket","ic_category_supermarket"),
                Category(CATEGORY_ID_BILLS, "Bills", "t_category_bills","ic_category_bills"),
                Category(CATEGORY_ID_SHOPPING, "Shopping", "t_category_shopping","ic_category_shopping"),
                Category(CATEGORY_ID_TRAVEL, "Travel", "t_category_travel","ic_category_travel"),
                Category(CATEGORY_ID_TRANSPORTATION, "Transportation", "t_category_transportation","ic_category_transportation"),
                Category(CATEGORY_ID_LEISURE, "Leisure", "t_category_leisure","ic_category_leisure"),
                Category(CATEGORY_ID_RENT, "Rent", "t_category_rent","ic_category_rent"),
                Category(CATEGORY_ID_OTHER, "Other", "t_category_other","ic_category_other")
        )
    }
}