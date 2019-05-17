package br.com.micdev.fid2.retrofit2.user

import br.com.micdev.fid2.retrofit2.RetrofitClient

object UserUtils {

    private val BASE_URL = "http://fid-api.sa-east-1.elasticbeanstalk.com/"

    val userService: UserService?
        get() = RetrofitClient.getClient(BASE_URL)?.create(UserService::class.java)
}
