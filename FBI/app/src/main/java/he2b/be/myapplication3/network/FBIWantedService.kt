package he2b.be.myapplication3.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FBIWantedService {
    @GET("wanted/v1/list")
    suspend fun getWanted(
        @Query("page") page: Int,
        @Header("User-Agent") userAgent: String
    ): Response<WantedResponse>

    @GET("@wanted-person/{uid}")
    suspend fun getDetailsByUID(
        @Path("uid") uid: String,
        @Header("User-Agent") userAgent: String
    ): Response<WantedItem>

    @GET("@wanted")
    suspend fun getPosterClassification(
        @Query("page") page: Int,
        @Query("poster_classification") poster_classification: String,
        @Header("User-Agent") userAgent: String,
    ):Response<WantedResponse>
}
