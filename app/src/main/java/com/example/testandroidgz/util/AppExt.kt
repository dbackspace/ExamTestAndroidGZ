package com.example.testandroidgz.util

import android.content.Context
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.MutableLiveData
import com.example.testandroidgz.MyApplication
import com.example.testandroidgz.model.local.LocalImage
import com.example.testandroidgz.model.remote.Image
import kotlinx.coroutines.CoroutineScope

fun Image.toLocalImage(): LocalImage {
    return LocalImage(
        id = this.id,
        width = this.width,
        height = this.height,
        url = this.src.large,
        photographerName = this.photographer,
        thumbnail = this.src.small
    )
}

val Context.applicationScope: CoroutineScope
    get() = (applicationContext as MyApplication).mApplicationScope

fun <T> MutableLiveData<T>.updateValue(newValue: T){
    if(Looper.myLooper() == Looper.getMainLooper()){
        value = newValue
    } else{
        postValue(newValue)
    }
}

fun View?.setVisible(isVisible: Boolean) {
    this?.visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}