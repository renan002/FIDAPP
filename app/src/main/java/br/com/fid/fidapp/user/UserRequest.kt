package br.com.fid.fidapp.user

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
    @SerializedName("birthDate")
    val dataNasc:String,

    @Expose
    @SerializedName("name")
    val name:String,

    @Expose
    @SerializedName("password")
    val password:String

)