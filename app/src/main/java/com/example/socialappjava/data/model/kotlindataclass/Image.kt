package com.example.socialappjava.data.model.kotlindataclass

data class Image(
    val aspectRatio: Double,
    val id: Int,
    val imageUrl: String,
    val thumbnailUrl: String,
    val type: Int
)