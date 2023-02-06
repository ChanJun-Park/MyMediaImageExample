package com.example.mymediaimageexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.example.mymediaimageexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
	private lateinit var mediaImageLoader: MediaImageLoader

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		mediaImageLoader = MediaImageLoader(applicationContext)

		mediaImageLoader.getMediaImageCursor().use { cursor ->
			if (cursor != null && cursor.moveToNext()) {
				cursor.columnNames.forEach {
					binding.textContainer.addView(createTextView("columnName: $it"))
					binding.textContainer.addView(createTextView("\n"))
				}
			}
		}
	}

	private fun createTextView(text: String): TextView {
		return TextView(this).apply {
			layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
			this.text = text
		}
	}
}