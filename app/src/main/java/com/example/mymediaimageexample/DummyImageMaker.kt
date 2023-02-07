package com.example.mymediaimageexample

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Environment
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream

class DummyImageMaker(private val context: Context) {

	fun createDummyImage() {
		val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
		val canvas = Canvas(bitmap)

		canvas.drawColor(Color.RED)

		val folderPath = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/TEST"
		val folder = File(folderPath)
		if (!folder.exists()) {
			folder.mkdir()
		}

		val file = File(folder, "test.png")

		FileOutputStream(file.absolutePath).use { fileOutputStream ->
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
			fileOutputStream.flush()
		}

		val values = ContentValues().apply {
			put(MediaStore.Images.Media.DISPLAY_NAME, "test.png")
			put(MediaStore.Images.Media.MIME_TYPE, "image/png")
			put(MediaStore.Images.Media.DATA, file.absolutePath)
		}

		val itemUri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

		println(itemUri)
//		itemUri?.let {
//			context.contentResolver.openFileDescriptor(it, "w", null)?.use {parcelFileDescriptor ->
//				 FileOutputStream(parcelFileDescriptor.fileDescriptor).use { fileOutputStream ->
//					 bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
//					 fileOutputStream.flush()
//				 }
//			}
//		}
	}
}