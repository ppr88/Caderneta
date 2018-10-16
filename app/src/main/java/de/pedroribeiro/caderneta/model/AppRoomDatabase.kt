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


@Database(entities = [Expense::class, Category::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppRoomDatabase : RoomDatabase() {

    protected val TAG = "AppRoomDatabase"

    abstract fun expenseDao(): ExpenseDao
    abstract fun categoryDao(): CategoryDao

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
                PopulateDbAsync(it).execute(Category.defaultCategories, Expense.samples)
            } ?: throw NullPointerException("Tried to populate db before initialization")
        }
    }

    //Populate the DB on a separate thread
    class PopulateDbAsync(private val db: AppRoomDatabase) : AsyncTask<List<Any>, Unit, Unit>() {
        override fun doInBackground(vararg params: List<Any>) {
            Log.d(db.TAG, "Populating database")
            db.categoryDao().deleteAll()
            db.categoryDao().insertAll(params[0] as List<Category>)
            db.expenseDao().deleteAll()
            db.expenseDao().insertAll(params[1] as List<Expense>)
            return
        }
    }
}