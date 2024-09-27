package he2b.be.myapplication3.wantedDataBase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wanted_items")
data class WantedItemEntity(
    @PrimaryKey
    val uid: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "age")
    val age: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "images")
    val images: String
)