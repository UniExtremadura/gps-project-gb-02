package es.unex.giss.asee.whichnews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.unex.giss.asee.whichnews.data.models.User

@Database(entities = [User::class], version = 1)
abstract class WhichNewsDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        private var INSTANCE: WhichNewsDatabase? = null
        fun getInstance(context: Context): WhichNewsDatabase? {
            if (INSTANCE == null) {
                synchronized(WhichNewsDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        WhichNewsDatabase::class.java, "whichnews.db"
                    ).build()
                }
            }
            return INSTANCE
        }
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}