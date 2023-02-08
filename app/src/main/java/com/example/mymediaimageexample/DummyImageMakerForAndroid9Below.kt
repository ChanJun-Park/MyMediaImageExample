package com.example.mymediaimageexample

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Environment
import android.provider.MediaStore
import java.io.File
import java.io.FileOutputStream

// 안드로이드 9 이하에서는 외부 저장소에 데이터를 쓰거나 읽을때 저장소 권한(WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE)이 필요
class DummyImageMakerForAndroid9Below(private val context: Context) {

	fun createDummyImage() {
		val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
		val canvas = Canvas(bitmap)

		canvas.drawColor(Color.RED)

//		공용 폴더에 저장, 앱이 지워져도 이 파일은 지워지지 않음, 공용 저장소/Pictures/TEST 서브 폴더에 저장됨
//		val folderPath = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/TEST"

//		앱의 외부 저장소에 저장. 앱이 지워지면 이 파일도 함께 지워짐. 앱 외부 저장소/Pictures/TEST 서브 폴더에 저장됨
		val folderPath = "${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}/TEST"

//		앱의 외부 저장소에 저장. 앱이 지워지면 이 파일도 함께 지워짐. 앱 외부 저장소/Pictures 폴더에 저장됨
//		val folderPath = "${context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)}"
		val folder = File(folderPath)
		if (!folder.exists()) {
			folder.mkdir()
		}

		val file = File(folder, "test.png")

		println(file.absolutePath)

//		Media Store 에 저장하기 전에 미리 파일에 데이터를 넣을 수 있다.
//		FileOutputStream(file.absolutePath).use { fileOutputStream ->
//			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
//			fileOutputStream.flush()
//		}

		val values = ContentValues().apply {
			put(MediaStore.Images.Media.DISPLAY_NAME, "test.png")
			put(MediaStore.Images.Media.MIME_TYPE, "image/png")
			put(MediaStore.Images.Media.DATA, file.absolutePath)
		}

//		Media 저장소의 외부 공용 저장소 위치 지정시 Android 10 미만에서 MediaStore.Images.Media.EXTERNAL_CONTENT_URI?
		val itemUri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

		println(itemUri)
		itemUri?.let {
//			contentResolver 를 통해 FileDescriptor 를 열어서 데이터를 저장할 수 있다.
			context.contentResolver.openFileDescriptor(it, "w", null)?.use {parcelFileDescriptor ->
				 FileOutputStream(parcelFileDescriptor.fileDescriptor).use { fileOutputStream ->
					 bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
					 fileOutputStream.flush()
				 }
			}
		}
	}
}