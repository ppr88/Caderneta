package de.pedroribeiro.caderneta.model

import android.arch.persistence.room.TypeConverter
import java.lang.IllegalArgumentException
import java.util.*


/**
 * Converters for types that are not native to Room
 * See: https://developer.android.com/reference/android/arch/persistence/room/TypeConverter
 */
class Converters {
    companion object {
        @TypeConverter
        @JvmStatic
        fun dateFromTimestamp(value: Long): Date {
            return Date(value)
        }


        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: Date): Long {
            return date.time
        }

        @TypeConverter
        @JvmStatic
        fun categoryFromString(value: String): Expense.Category {
            return try {
                Expense.Category.valueOf(value)
            } catch (e: IllegalArgumentException) {
                Expense.Category.Other
            }
        }

        @TypeConverter
        @JvmStatic
        fun categoryToString(category: Expense.Category): String {
            return category.name
        }
    }
}
