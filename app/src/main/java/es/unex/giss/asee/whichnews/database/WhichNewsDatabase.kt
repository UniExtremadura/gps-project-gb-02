package es.unex.giss.asee.whichnews.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.unex.giss.asee.whichnews.data.models.User
import es.unex.giss.asee.whichnews.data.models.NewsLike

@Database(entities = [User::class, NewsLike::class], version = 2, exportSchema = false)
abstract class WhichNewsDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun newsLikeDao(): NewsLikeDao

    companion object {
        private var INSTANCE: WhichNewsDatabase? = null

        fun getInstance(context: Context): WhichNewsDatabase {
            synchronized(WhichNewsDatabase::class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        WhichNewsDatabase::class.java, "whichnews.db"
                    ).build()
                }
                return INSTANCE as WhichNewsDatabase
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
