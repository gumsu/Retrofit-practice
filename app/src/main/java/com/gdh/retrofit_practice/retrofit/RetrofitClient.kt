package com.gdh.retrofit_practice.retrofit

import android.util.Log
import com.gdh.retrofit_practice.utils.Constants.TAG
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 싱글턴
object RetrofitClient {
    // 레트로핏 클라이언트 선언
    // private lateinit var retrofitClient : Retrofit
    private var retrofitClient : Retrofit? = null

    // 레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient() called")

        if(retrofitClient == null){

            // 레트로핏 빌더를 통해 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofitClient
    }
}