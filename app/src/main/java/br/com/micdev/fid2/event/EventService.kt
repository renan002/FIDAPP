package br.com.micdev.fid2.event

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface EventService {

    @GET("api/event")
    fun eventGet(@Header("Authorization") token:String): Call<EventResponse>

}