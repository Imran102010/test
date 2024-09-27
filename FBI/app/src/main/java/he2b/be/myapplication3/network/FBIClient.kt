package he2b.be.myapplication3.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object FBIClient {
    private val gson: Gson = GsonBuilder().create()

    val api: FBIWantedService = Retrofit.Builder()
        .baseUrl("https://api.fbi.gov/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(FBIWantedService::class.java)
}