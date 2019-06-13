package br.com.fid.fidapp.event

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface EventService {

    @GET("api/event")
    fun eventGet(@Header("Authorization") token:String?): Call<EventResponse>

    @GET
    fun eventGetOne(@Header("Authorization") token: String?, @Url url:String): Call<EventObject>


}