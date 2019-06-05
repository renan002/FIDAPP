package br.com.micdev.fid2.invite

import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface InviteService {

    @POST("api/invite")
    fun invitePost(@Header("Authorization") token:String, @Query("eventCode")eventCode:String): Call<Unit>
}