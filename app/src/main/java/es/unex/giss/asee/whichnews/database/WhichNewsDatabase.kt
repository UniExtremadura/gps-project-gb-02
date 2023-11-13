package es.unex.giss.asee.whichnews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.unex.giss.asee.whichnews.data.models.Review
import es.unex.giss.asee.whichnews.data.models.User

@Database(entities = [User::class, Review::class], version = 1)
abstract class WhichNewsDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun reviewDao(): ReviewDao

    companion object {
        private var INSTANCE: WhichNewsDatabase? = null

        fun getInstance(context: Context): WhichNewsDatabase {
            return INSTANCE ?: synchronized(WhichNewsDatabase::class.java) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): WhichNewsDatabase {
            return Room.databaseBuilder(
                context,
                WhichNewsDatabase::class.java, "whichnews.db"
            ).build()
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
