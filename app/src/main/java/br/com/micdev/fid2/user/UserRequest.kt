package br.com.micdev.fid2.user

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserRequest (
    @Expose
    @SerializedName("cpf")
    val cpf:String,

    @Expose
    @SerializedName("email")
    val email:String,

    @Expose
    @SerializedName("name")
    val name:String,

    @Expose
    @SerializedName("password")
    val password:String

)