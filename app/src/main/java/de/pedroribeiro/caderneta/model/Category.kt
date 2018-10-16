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
        val defaultCategories: List<Category> = listOf(
                Category(0, "Food", "t_category_food","ic_category_food"),
                Category(1, "Supermarket", "t_category_supermarket","ic_category_supermarket"),
                Category(2, "Bills", "t_category_bills","ic_category_bills"),
                Category(3, "Shopping", "t_category_shopping","ic_category_shopping"),
                Category(4, "Travel", "t_category_travel","ic_category_travel"),
                Category(5, "Transportation", "t_category_transportation","ic_category_transportation"),
                Category(6, "Leisure", "t_category_leisure","ic_category_leisure"),
                Category(7, "Rent", "t_category_rent","ic_category_rent"),
                Category(8, "Other", "t_category_other","ic_category_other")
        )
    }
}