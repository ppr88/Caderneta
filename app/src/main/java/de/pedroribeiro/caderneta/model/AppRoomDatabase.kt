package de.pedroribeiro.caderneta.model

import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import java.lang.NullPointerException


@Database(entities = arrayOf(Expense::class), version = 1)
@TypeConverters(Converters::class)
abstract class AppRoomDatabase : RoomDatabase() {

    protected val TAG = "AppRoomDatabase"

    abstract fun expenseDao(): ExpenseDao

    companion object {
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context) : AppRoomDatabase {

            if (INSTANCE == null) {
                INSTANCE = Room.inMemoryDatabaseBuilder(context.applicationContext,
                        AppRoomDatabase::class.java)
                        .addCallback(DbPopulator)
                        .build()
            }
            return INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }

    //Singleton to populate the DB with dummy values for test purposes
    object DbPopulator : RoomDatabase.Callback() {
        @Override
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let {
                PopulateDbAsync(it).execute(Expense.samples)
            } ?: throw NullPointerException("Tried to populate db before initialization")
        }
    }

    //Populate the DB on a separate thread
    class PopulateDbAsync(private val db: AppRoomDatabase) : AsyncTask<List<Expense>, Unit, Unit>() {
        override fun doInBackground(vararg params: List<Expense>): Unit {
            Log.d(db.TAG, "Populating database")
            db.expenseDao().deleteAll()
            db.expenseDao().insertAll(params[0])
            return
        }
    }
}