package com.example.socialappjava.data.model.kotlindataclass

data class Data(
    val attachments: List<Attachment>,
    val caption: String,
    val category: Category,
    val description: String,
    val id: Int,
    val image: Image,
    val media: List<Media>,
    val publishedOn: String,
    val shareText: String,
    val title: String,
    val type: Int,
    val uniqueId: String,
    val url: String,
    val video: Video
)