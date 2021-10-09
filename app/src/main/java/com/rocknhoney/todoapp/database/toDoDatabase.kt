package com.rocknhoney.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rocknhoney.todoapp.dao.toDoDao
import com.rocknhoney.todoapp.model.toDo

@Database(entities = [toDo::class], version = 1)
abstract class toDoDatabase : RoomDatabase() {

    abstract fun todoDao(): toDoDao

    companion object {

        private var DATABASE_NAME: String = "toDo_database"

        @Volatile
        private var INSTANCE: toDoDatabase? = null

        fun getInstance(context: Context): toDoDatabase? {
            if (INSTANCE != null) {
                return INSTANCE
            }
            synchronized(this) {
                INSTANCE =
                    Room.databaseBuilder(
                        context,
                        toDoDatabase::class.java,
                        DATABASE_NAME
                    ).allowMainThreadQueries()
                        .build()
                return INSTANCE
            }
        }
    }

    open fun destroyInstance() {
        INSTANCE = null
    }
}