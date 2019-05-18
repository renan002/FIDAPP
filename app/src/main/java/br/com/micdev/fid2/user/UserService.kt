package br.com.micdev.fid2.user

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("api/user/register")
    fun registrationPost(@Body request: RequestBody): Call<UserResponse>
}