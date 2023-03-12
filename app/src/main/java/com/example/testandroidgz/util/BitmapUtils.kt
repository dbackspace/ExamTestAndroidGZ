package com.example.testandroidgz.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object BitmapUtils {
    private const val TAG = "BitmapUtils"

    private val thumbnailCache = mutableMapOf<Int, Bitmap?>()
    private val originalImageCache = mutableMapOf<Int, Bitmap?>()

    suspend fun getThumbnail(
        context: Context,
        id: Int,
        url: String,
        isThumbnail: Boolean
    ): Bitmap? {
        return (if (isThumbnail) thumbnailCache[id] else originalImageCache[id])
            ?: tryToDownloadBitmap(context, id, url, isThumbnail)
    }

    private suspend fun tryToDownloadBitmap(
        context: Context,
        id: Int,
        url: String,
        isThumbnail: Boolean
    ): Bitmap? {
        val exceptionHandler = CoroutineExceptionHandler { _, _ ->
            Log.d(TAG, "load bitmap fail: id = $id")
        }
        val asyncBitmap = context.applicationScope.async(Dispatchers.IO + exceptionHandler) {
            val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream: InputStream = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(inputStream)
            if (bitmap != null) {
                addThumbnailToCache(id, bitmap, isThumbnail)
            }
            bitmap
        }
        return asyncBitmap.await()
    }

    private fun addThumbnailToCache(id: Int, bitmap: Bitmap, isThumbnail: Boolean) {
        if (isThumbnail) {
            thumbnailCache[id] = bitmap
        } else {
            originalImageCache[id] = bitmap
        }
    }
}