package com.example.mymediaimageexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mymediaimageexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	private lateinit var mediaImageCursorLoader: MediaImageCursorLoader
	private lateinit var dummyImageMakerForAndroid9Below: DummyImageMakerForAndroid9Below

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

//		mediaImageCursorLoader = MediaImageCursorLoader(applicationContext)
//		mediaImageCursorLoader.getMediaImageCursor().use { cursor ->
//			if (cursor != null && cursor.moveToNext()) {
//				cursor.columnNames.forEach {
//					binding.textContainer.addView(createTextView("columnName: $it"))
//					binding.textContainer.addView(createTextView("\n"))
//				}
//			}
//		}

		binding.button.setOnClickListener {
			dummyImageMakerForAndroid9Below = DummyImageMakerForAndroid9Below(this)
			dummyImageMakerForAndroid9Below.createDummyImage()
		}
	}

	private fun createTextView(text: String): TextView {
		return TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
			this.text = text
		}
	}
}