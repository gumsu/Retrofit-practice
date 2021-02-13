package com.gdh.retrofit_practice.utils

import android.widget.EditText
import android.text.Editable
import android.text.TextWatcher
import java.text.SimpleDateFormat
import java.util.*

// 문자열이 json 형태인지
fun String?.isJsonObject(): Boolean {
    // return this?.startsWith("{") == true && this.endsWith("}")
    if(this?.startsWith("{") == true && this.endsWith("}")){
        return true
    } else {
        return false
    }
}

// 문자열이 jsonArray 형태인지
fun String?.isJsonArray(): Boolean {
    if(this?.startsWith("[") == true && this.endsWith("]")){
        return true
    } else{
        return false
    }
}

// 날짜 포맷
fun Date.toString() : String{
    val format = SimpleDateFormat("HH:mm:ss")
    return format.format(this)
}

// editText에 대한 extension
fun EditText.onMyTextChanged(completion: (Editable?) -> Unit){
    this.addTextChangedListener(object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            completion(editable)
        }
    })
}