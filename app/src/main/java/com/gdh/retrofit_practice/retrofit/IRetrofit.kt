package com.gdh.retrofit_practice.retrofit

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IRetrofit {

    // https://api.unsplash.com/search/photos/?query=""

    @GET("/search/photos")
    fun searchPhotos(@Query("query") searchTerm: String): Call<JsonElement>

    @GET("/search/users")
    fun searchUsers(@Query("query") searchTerm: String): Call<JsonElement>
}