package br.com.fid.fidapp.retrofit

import br.com.fid.fidapp.event.EventService
import br.com.fid.fidapp.invite.InviteService
import br.com.fid.fidapp.login.LoginService
import br.com.fid.fidapp.user.UserService

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