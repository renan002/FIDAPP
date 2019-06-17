package br.com.fid.fidapp.event

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface EventService {

    @GET("api/event/list")
    fun eventGet(@Header("Authorization") token:String?): Call<EventResponse>

    @GET("api/event")
    fun eventGetOne(@Header("Authorization") token: String?, @Query("eventCode") eventCode:String): Call<EventObject>

}