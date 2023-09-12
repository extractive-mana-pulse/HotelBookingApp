package com.example.androidjobtask.presentation.api

import com.example.androidjobtask.presentation.data.BookingDate
import com.example.androidjobtask.presentation.data.NumberPageDate
import com.example.androidjobtask.presentation.data.date
import retrofit2.Call
import retrofit2.http.GET

interface API {

    @GET("v3/35e0d18e-2521-4f1b-a575-f0fe366f66e3")
    fun getData(): Call<date>

    @GET("v3/f9a38183-6f95-43aa-853a-9c83cbb05ecd")
    fun getRooms(): Call<NumberPageDate>

    @GET("v3/e8868481-743f-4eb2-a0d7-2bc4012275c8")
    fun getDataB(): Call<BookingDate>
}