package com.example.mymediaimageexample

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore

class MediaImageCursorLoader(private val context: Context) {
	fun getMediaImageCursor(): Cursor? = context.contentResolver.query(
		/* uri = */ MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
		/* projection = */ null,
		/* selection = */ null,
		/* selectionArgs = */ null,
		/* sortOrder = */ null
	)
}