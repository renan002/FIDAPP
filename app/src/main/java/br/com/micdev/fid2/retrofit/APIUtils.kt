package br.com.micdev.fid2.retrofit

import br.com.micdev.fid2.event.EventService
import br.com.micdev.fid2.invite.InviteService
import br.com.micdev.fid2.login.LoginService
import br.com.micdev.fid2.user.UserService
import retrofit2.create

object APIUtils {

    val userService: UserService
        get() = RetrofitClient.getClient()!!.create(UserService::class.java)

    val loginService: LoginService
        get() = RetrofitClient.getClient()!!.create(LoginService::class.java)

    val eventService: EventService
        get() = RetrofitClient.getClient()!!.create(EventService::class.java)

    val inviteService: InviteService
        get() = RetrofitClient.getClient()!!.create(InviteService::class.java)
}