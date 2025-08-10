package com.hyosimroad.hamkkae.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream

object GallerySaver {

    suspend fun saveUris(
        context: Context,
        uris: List<Uri>,
        subDir: String = "Pictures/MyApp"
    ): Int = withContext(Dispatchers.IO) {
        var saved = 0
        for ((idx, src) in uris.withIndex()) {
            runCatching {
                context.contentResolver.openInputStream(src)?.use { inS ->
                    val name = "photo_${System.currentTimeMillis()}_${idx}.jpg"
                    saveStream(context, inS, name, subDir)
                }
            }.onSuccess { saved++ }
        }
        saved
    }

    suspend fun saveBitmaps(
        context: Context,
        bitmaps: List<Bitmap>,
        subDir: String = "Pictures/MyApp"
    ): Int = withContext(Dispatchers.IO) {
        var saved = 0
        for ((idx, bmp) in bitmaps.withIndex()) {
            runCatching {
                val name = "photo_${System.currentTimeMillis()}_${idx}.jpg"
                val pipe = PipedOutputStream()
                val inS = PipedInputStream(pipe)
                // 비동기로 JPEG로 압축해 파이프에 씀
                launch {
                    bmp.compress(Bitmap.CompressFormat.JPEG, 95, pipe)
                    pipe.close()
                }
                saveStream(context, inS, name, subDir)
            }.onSuccess { saved++ }
        }
        saved
    }

    suspend fun saveUrls(
        context: Context,
        urls: List<String>,
        subDir: String = "Pictures/MyApp"
    ): Int = withContext(Dispatchers.IO) {
        var saved = 0
        val ok = okhttp3.OkHttpClient()
        for ((idx, u) in urls.withIndex()) {
            runCatching {
                val resp = ok.newCall(
                    okhttp3.Request.Builder().url(u).build()
                ).execute()
                resp.body?.byteStream()?.use { inS ->
                    val ext = guessExt(resp.body?.contentType()?.toString())
                    val name = "photo_${System.currentTimeMillis()}_${idx}.${ext}"
                    saveStream(context, inS, name, subDir,
                        mime = mimeFromExt(ext))
                }
            }.onSuccess { saved++ }
        }
        saved
    }

    private fun guessExt(ct: String?): String =
        when {
            ct?.contains("png", true) == true -> "png"
            ct?.contains("webp", true) == true -> "webp"
            else -> "jpg"
        }
    private fun mimeFromExt(ext: String) =
        when (ext.lowercase()) {
            "png" -> "image/png"
            "webp" -> "image/webp"
            else -> "image/jpeg"
        }

    private fun saveStream(
        context: Context,
        inS: InputStream,
        displayName: String,
        subDir: String,
        mime: String = "image/jpeg"
    ) {
        val resolver = context.contentResolver
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
            put(MediaStore.Images.Media.MIME_TYPE, mime)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, subDir) // 예: Pictures/MyApp
                put(MediaStore.Images.Media.IS_PENDING, 1)
            } else {
                val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val file = File(dir, displayName)
                put(MediaStore.Images.Media.DATA, file.absolutePath)
            }
        }

        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            ?: throw IllegalStateException("Failed to create MediaStore record")

        resolver.openOutputStream(uri)?.use { outS ->
            inS.copyTo(outS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            values.clear()
            values.put(MediaStore.Images.Media.IS_PENDING, 0)
            resolver.update(uri, values, null, null)
        }
    }
}
