package br.com.micdev.fid2.retrofit2.user

import br.com.micdev.fid2.UserModel
import br.com.micdev.fid2.retrofit2.RetrofitClient
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface UserService {

    @POST("/api/user/register")
    fun savePost(
        @Body userModel: UserModel
    ): Call<RetrofitClient>
}
