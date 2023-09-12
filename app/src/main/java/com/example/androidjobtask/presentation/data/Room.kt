package com.example.androidjobtask.presentation.data

import com.google.gson.annotations.SerializedName

data class Room(
    val id: Int,
    @SerializedName("image_urls")
    val url: List<String>,
    val name: String,
    val peculiarities: List<String>,
    val price: Int,
    @SerializedName("price_per")
    val price_information: String
)