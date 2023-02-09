package com.example.mymediaimageexample

import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore

class MediaImageLoaderImpl(
	private val mediaImageCursorLoader: MediaImageCursorLoader
): MediaImageLoader {

	override suspend fun getMediaImages(): List<MediaImage> {
		val imageList = mutableListOf<MediaImage>()
		mediaImageCursorLoader.getMediaImageCursor()?.use { cursor ->
			val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
			val bucketIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID)
			val dateModifiedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)
			val mimeTypeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE)
			val orientationColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION)

			while (cursor.moveToNext()) {
				val id = cursor.getLong(idColumn)
				val bucketId = cursor.getInt(bucketIdColumn)
				val dateModified = cursor.getLong(dateModifiedColumn)
				val mimeType = cursor.getString(mimeTypeColumn) ?: ""
				val orientation = cursor.getInt(orientationColumn)

				val contentUri: Uri = ContentUris.withAppendedId(
					/* contentUri = */ MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					/* id = */ id
				)

				imageList += MediaImage(id, bucketId, contentUri, mimeType, dateModified, orientation)
			}
		}

		return imageList
	}
}