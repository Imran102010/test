package he2b.be.myapplication3.repository

import android.content.Context
import he2b.be.myapplication3.wantedDataBase.WantedDatabase
import he2b.be.myapplication3.wantedDataBase.WantedItemEntity

object Repository {
    private lateinit var database: WantedDatabase

    fun initDatabase(context: Context) {
        if (!::database.isInitialized) {
            database = WantedDatabase.getInstance(context)
        }
    }

    fun insertWantedItemInDatabase(item: WantedItemEntity) {
        database.wantedItemDao().insertItem(item)
    }

    fun getAllWantedItemsFromDatabase(): List<WantedItemEntity> {
        database.let { theDatabase ->
            return theDatabase.wantedItemDao().getAllItems()
        }
    }

    suspend fun deleteWantedItemFromDatabase(item: WantedItemEntity){
        database.wantedItemDao().delete(item)
    }
}