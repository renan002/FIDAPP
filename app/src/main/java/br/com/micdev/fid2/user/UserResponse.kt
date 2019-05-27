package br.com.micdev.fid2.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse(
    @Expose
    @SerializedName("code")
    val code: Int,

    @Expose
    @SerializedName("message")
    val message:String,

    @Expose
    @SerializedName("url")
    val url:String,

    @Expose
    @SerializedName("protocol")
    val protocol:String
)