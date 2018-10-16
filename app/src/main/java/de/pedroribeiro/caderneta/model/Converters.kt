package de.pedroribeiro.caderneta.model

import androidx.room.TypeConverter
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
    }
}
