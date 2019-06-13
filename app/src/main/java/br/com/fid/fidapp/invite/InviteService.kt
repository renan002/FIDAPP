package br.com.fid.fidapp.invite

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface InviteService {

    @POST("api/invite")
    fun invitePost(@Header("Authorization") token:String, @Query("eventCode")eventCode:String): Call<Unit>

    @GET("api/invite")
    fun inviteGet(@Header("Authorization") token:String): Call<InviteResponseGet>
}