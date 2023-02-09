package com.example.mymediaimageexample

interface MediaImageLoader {
	suspend fun getMediaImages(): List<MediaImage>
}