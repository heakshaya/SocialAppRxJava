package com.example.socialappjava.data.model.kotlindataclass

data class Video(
    val aspectRatio: Double,
    val id: Int,
    val thumbnailUrl: String,
    val type: Int,
    val videoUrl: String
)