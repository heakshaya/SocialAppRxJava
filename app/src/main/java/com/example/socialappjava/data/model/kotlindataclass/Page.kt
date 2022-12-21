package com.example.socialappjava.data.model.kotlindataclass

data class Page(
    val limit: Int,
    val next: Boolean,
    val offset: Int,
    val totalRecords: Int
)