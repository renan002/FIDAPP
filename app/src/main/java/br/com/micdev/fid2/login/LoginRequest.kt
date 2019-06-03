package br.com.micdev.fid2.login

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginRequest (

    @Expose
    @SerializedName("cpf")
    val cpf:String,

    @Expose
    @SerializedName("password")
    val senha:String
)