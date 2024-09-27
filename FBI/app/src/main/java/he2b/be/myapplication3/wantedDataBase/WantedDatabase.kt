package he2b.be.myapplication3.wantedDataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [WantedItemEntity::class], version = 4, exportSchema = false)
abstract class WantedDatabase : RoomDatabase() {
    abstract fun wantedItemDao(): WantedItemDao

    companion object {
        @Volatile
        private var instance: WantedDatabase? = null
        private const val DATABASE_NAME = "wanted_database"

        fun getInstance(context: Context): WantedDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    WantedDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build().also { instance = it }
            }
        }
    }
}

