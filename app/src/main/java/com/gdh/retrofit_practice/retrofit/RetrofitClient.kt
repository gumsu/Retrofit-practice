package com.gdh.retrofit_practice.retrofit

import android.util.Log
import com.gdh.retrofit_practice.utils.Constants.TAG
import com.gdh.retrofit_practice.utils.isJsonArray
import com.gdh.retrofit_practice.utils.isJsonObject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

// 싱글턴
object RetrofitClient {
    // 레트로핏 클라이언트 선언
    // private lateinit var retrofitClient : Retrofit
    private var retrofitClient : Retrofit? = null

    // 레트로핏 클라이언트 가져오기
    fun getClient(baseUrl: String): Retrofit? {
        Log.d(TAG, "RetrofitClient - getClient() called")

        // okhhtp 인스턴스 생성
        val client = OkHttpClient.Builder()

        // 로그를 찍기 위해 로깅 인터셉터 설정
       val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
           override fun log(message: String) {
               Log.d(TAG, "RetrofitClient - log() called / message: $message ")

               when {
                   message.isJsonObject() ->
                       Log.d(TAG, JSONObject(message).toString(4))
                   message.isJsonArray() ->
                       Log.d(TAG, JSONObject(message).toString(4))
                   else -> {
                       try {
                           Log.d(TAG, JSONObject(message).toString(4))
                       } catch (e: Exception) {
                           Log.d(TAG, message)
                       }
                   }
               }
           }
       })

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        // 위에서 설정한 로깅 인터셉터를 okhttp 클라이언트에 추가
        client.addInterceptor(loggingInterceptor)

        if(retrofitClient == null){

            // 레트로핏 빌더를 통해 인스턴스 생성
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())

                    // 위에서 설정한 클라이언트로 레트로핏 클라이언트를 설정한다.
                .client(client.build())
                .build()
        }
        return retrofitClient
    }
}