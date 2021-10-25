package br.com.ymc.gamesave.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.ymc.gamesave.db.dao.GameDAO
import br.com.ymc.gamesave.db.model.GameDB

@Database(entities = [GameDB::class], version = 4)
abstract class AppDatabase : RoomDatabase()
{
    abstract fun getGameDao(): GameDAO

    companion object
    {
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDBInstance(context: Context) : AppDatabase
        {
            if(INSTANCE == null)
            {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "gamesaver.sqlite")
                    .addMigrations(migration_1_2)
                    .addMigrations(migration_2_3)
                    .addMigrations(migration_3_4)
                    .build()
            }

            return INSTANCE!!
        }

        private val migration_1_2: Migration = object : Migration(1, 2)
        {
            override fun migrate(database: SupportSQLiteDatabase)
            {
                database.execSQL("ALTER TABLE GAME ADD COLUMN summary TEXT NOT NULL DEFAULT ''")
            }
        }

        private val migration_2_3: Migration = object : Migration(2, 3)
        {
            override fun migrate(database: SupportSQLiteDatabase)
            {
                database.execSQL("ALTER TABLE GAME ADD COLUMN releaseDate Integer")
            }
        }

        private val migration_3_4: Migration = object : Migration(3, 4)
        {
            override fun migrate(database: SupportSQLiteDatabase)
            {
                database.execSQL("ALTER TABLE GAME ADD COLUMN rating REAL")
            }
        }
    }
}