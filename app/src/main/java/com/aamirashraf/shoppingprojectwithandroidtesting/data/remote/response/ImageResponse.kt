package com.aamirashraf.shoppingprojectwithandroidtesting.data.remote.response

data class ImageResponse(
    val hits: List<ImageResults>,
    val total: Int,
    val totalHits: Int
)