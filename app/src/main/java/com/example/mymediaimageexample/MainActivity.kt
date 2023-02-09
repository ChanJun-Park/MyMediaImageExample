package com.example.mymediaimageexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.example.mymediaimageexample.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

	private lateinit var binding: ActivityMainBinding
//	private lateinit var mediaImageCursorLoader: MediaImageCursorLoader
//	private lateinit var mediaImageLoader: MediaImageLoader
//	private lateinit var dummyImageMakerForAndroid9Below: DummyImageMakerForAndroid9Below

	private val viewModel by viewModels<MainViewModel> {
		MainViewModel.Factory(MediaImageLoaderImpl(MediaImageCursorLoader(applicationContext)))
	}

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

//		binding.button.setOnClickListener {
//			dummyImageMakerForAndroid9Below = DummyImageMakerForAndroid9Below(this)
//			dummyImageMakerForAndroid9Below.createDummyImage()
//		}

		lifecycleScope.launch {
			repeatOnLifecycle(Lifecycle.State.STARTED) {
				viewModel.images.collectLatest(::showImages)
			}
		}
	}

	private fun showImages(images: List<MediaImage>) {
		images.forEach {
			val imageView = ImageView(this).apply {
				layoutParams = ViewGroup.LayoutParams(500, 500)
			}

			binding.textContainer.addView(imageView)

			Glide.with(this)
				.load(it.contentUri)
				.into(imageView)
		}
	}

//	private fun createTextView(text: String): TextView {
//		return TextView(this).apply {
//			layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//			this.text = text
//		}
//	}
}