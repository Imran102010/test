package he2b.be.myapplication3.wantedDataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface WantedItemDao {
    @androidx.room.Query("SELECT * FROM wanted_items")
    fun getAllItems(): List<WantedItemEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertItem(item: WantedItemEntity)

    @Delete
    suspend fun delete(item: WantedItemEntity)
}
