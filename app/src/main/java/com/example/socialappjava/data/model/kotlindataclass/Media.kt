package com.example.socialappjava.data.model.kotlindataclass

data class Media(
    val aspectRatio: Double,
    val id: Int,
    val imageUrl: String,
    val thumbnailUrl: String,
    val type: Int,
    val videoUrl: String
)