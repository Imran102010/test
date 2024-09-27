package he2b.be.myapplication3.network

import com.google.gson.annotations.SerializedName

data class WantedItem(
    val title: String,
    val images: List<ImageData>,
    val name: String?,
    val age: String?,
    val description: String?,
    val uid: String,
    val caution:String?,
    @SerializedName("field_offices") val fieldOffice: List<String>?,
    val poster_classification: String?,
)

data class WantedResponse(val items: List<WantedItem>)
data class ImageData(val original: String)
