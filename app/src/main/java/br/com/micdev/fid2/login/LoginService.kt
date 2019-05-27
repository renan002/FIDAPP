package br.com.micdev.fid2.login

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/api/login")
    fun loginPost(@Body requestBody: RequestBody) : Call<LoginResponse>
}