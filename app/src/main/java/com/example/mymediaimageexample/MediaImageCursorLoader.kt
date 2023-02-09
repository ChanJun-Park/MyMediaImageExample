package com.example.mymediaimageexample

import android.content.Context
import android.database.Cursor
import android.os.Build
import android.provider.MediaStore

private val imageCollection =
	if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
		MediaStore.Images.Media.getContentUri(
			MediaStore.VOLUME_EXTERNAL
		)
	} else {
		MediaStore.Images.Media.EXTERNAL_CONTENT_URI
	}


private val projection = arrayOf(
	MediaStore.Images.Media._ID,
	MediaStore.Images.Media.BUCKET_ID,
	MediaStore.Images.Media.DATE_MODIFIED,
	MediaStore.Images.Media.MIME_TYPE,
	MediaStore.Images.Media.ORIENTATION
)

class MediaImageCursorLoader(private val context: Context) {
	fun getMediaImageCursor(): Cursor? = context.contentResolver.query(
		/* uri = */ imageCollection,
		/* projection = */ projection,
		/* selection = */ null,
		/* selectionArgs = */ null,
		/* sortOrder = */ null
	)
}