package br.com.micdev.fid2.retrofit

import br.com.micdev.fid2.user.UserService

object APIUtils {

    val userService: UserService
        get() = RetrofitClient.getClient()!!.create(UserService::class.java)
}