package com.aamirashraf.shoppingprojectwithandroidtesting.data.remote

import com.aamirashraf.shoppingprojectwithandroidtesting.BuildConfig
import com.aamirashraf.shoppingprojectwithandroidtesting.data.remote.response.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixelbayApi {
    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery: String,
        @Query("key") apikey:String = BuildConfig.API_KEY
    ):Response<ImageResponse>
}