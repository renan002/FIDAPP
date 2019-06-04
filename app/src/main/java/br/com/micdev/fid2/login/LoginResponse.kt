package br.com.micdev.fid2.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class LoginResponse (

    @Expose
    @SerializedName("login")
    val login:String,

    @Expose
    @SerializedName("name")
    val name:String,

    @Expose
    @SerializedName("token")
    val token: String,

    @Expose
    @SerializedName("userId")
    val userId:String
) : Serializable