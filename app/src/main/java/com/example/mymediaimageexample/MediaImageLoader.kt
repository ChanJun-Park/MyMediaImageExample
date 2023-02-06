package com.example.mymediaimageexample

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore

class MediaImageLoader(private val context: Context) {
	fun getMediaImageCursor(): Cursor? = context.contentResolver.query(
		MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
		null,
		null,
		null,
		null
	)
}