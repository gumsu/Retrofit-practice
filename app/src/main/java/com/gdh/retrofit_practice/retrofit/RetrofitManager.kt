package com.gdh.retrofit_practice.retrofit

import android.util.Log
import com.gdh.retrofit_practice.model.Photo
import com.gdh.retrofit_practice.utils.API
import com.gdh.retrofit_practice.utils.Constants.TAG
import com.gdh.retrofit_practice.utils.RESPONSE_STATE
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response
import java.text.SimpleDateFormat

class RetrofitManager {

    companion object{
        val instance = RetrofitManager()
    }

    // 레트로핏 인터페이스 가져오기
    private val iRetrofit: IRetrofit? = RetrofitClient.getClient(API.BASE_URL)?.create(IRetrofit::class.java)

    // 사진 검색 api 호출
    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, ArrayList<Photo>?) -> Unit){

        // val term = searchTerm ?: ""
        val term: String = searchTerm.let {
            it
        }?: ""

        // val call = iRetrofit?.searchPhotos(searchTerm = term) ?: return
        val call = iRetrofit?.searchPhotos(searchTerm = term).let {
            it
        }?: return

        // callback은 서버로부터 response 될 때마다 실행한다.
        call.enqueue(object : retrofit2.Callback<JsonElement> {
            // 응답 성공 시
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d(TAG, "RetrofitManager - onResponse() called / response: ${response.body()}")
//                completion(RESPONSE_STATE.OKAY, response.body().toString())

                when (response.code()) {
                    200 -> {
                        response.body()?.let {
                            var parsedPhotoDataArray = ArrayList<Photo>()

                            val body = it.asJsonObject
                            val results = body.getAsJsonArray("results")

                            val total = body.get("total").asInt

                            Log.d(TAG, "RetrofitManager - onResponse() called / total: $total")

                            results.forEach { resultItem ->
                                val resultItemObject = resultItem.asJsonObject

                                val user = resultItemObject.get("user").asJsonObject
                                val username: String = user.get("username").asString
                                val likesCount = resultItemObject.get("likes").asInt
                                val thumbnailLink = resultItemObject.get("urls").asJsonObject.get("thumb").asString
                                val createdAt = resultItemObject.get("created_at").asString

                                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                val formatter = SimpleDateFormat("yyyy년\nMM월 dd일")
                                val outputDateString = formatter.format(parser.parse(createdAt))
//                                Log.d(TAG, "RetrofitManager - outputDataString: $outputDateString")

                                val photoItem = Photo(author = username, likesCount = likesCount, thumnail = thumbnailLink, createdAt = outputDateString)

                                parsedPhotoDataArray.add(photoItem)
                            }

                            completion(RESPONSE_STATE.OKAY, parsedPhotoDataArray)
                        }
                    }
                }
            }

            // 응답 실패 시
            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d(TAG, "RetrofitManager - onFailure() called / t: $t ")
                completion(RESPONSE_STATE.FAIL, null)

            }
        })
    }
}