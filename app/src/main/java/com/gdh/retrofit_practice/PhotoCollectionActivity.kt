package com.gdh.retrofit_practice

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.gdh.retrofit_practice.utils.Constants.TAG

class PhotoCollectionActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_collection)

        Log.d(TAG, "PhotoCollectionActivity - onCreate() called ")
    }
}