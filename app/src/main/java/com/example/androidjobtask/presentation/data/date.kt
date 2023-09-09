package com.example.androidjobtask.presentation.data

import com.google.gson.annotations.SerializedName

data class date(

    val about_the_hotel: AboutTheHotel,

    val adress: String,

    val id: Int,

    @SerializedName("image_urls")
    val url: List<String>,

    val minimal_price: Int,

    val name: String,

    val price_for_it: String,

    val rating: Int,

    val rating_name: String
)