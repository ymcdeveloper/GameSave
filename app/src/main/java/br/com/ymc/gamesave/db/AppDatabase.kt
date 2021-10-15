package br.com.ymc.gamesave.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.ymc.gamesave.db.model.GameDB

@Database(entities = [GameDB::class], version = 1)
abstract class AppDatabase : RoomDatabase()
{
    companion object
    {
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDBInstance(context: Context) : AppDatabase
        {
            if(INSTANCE == null)
            {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "gamesaver.sqlite")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return INSTANCE!!
        }
    }
}